import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import {SharedService} from '../../services/shared.service';

declare var $: any;
declare var WOW: any;

@Component({
    selector: 'app-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit, AfterViewInit {

    public defaultLink: string = '/';
    public submittedListLink: string = '/submitted-list';
    public myAccountsLink: string = '/e-kyc-profile';
    public disableSidebar: boolean = false;
    public menuJson: Object[];
    public role = '';
    public resetRequired = '';
    public rmsRole = '';
    public awUserStatus = '';

    constructor(private route: ActivatedRoute, private router: Router, private sharedService: SharedService,
                private translate: TranslateService) {
        translate.setDefaultLang('bn');
    }

    ngOnInit() {
        if (this.route.snapshot.data.home) {
            this.route.data
                .subscribe(data => {
                    if (data.home[0]) {
                        this.menuJson = data.home[0].body.menuJson;
                        this.resetRequired = data.home[0].body.resetRequired;
                        this.role = data.home[0].body.roles[0];
                        this.rmsRole = data.home[0].body.roleOid;
                        this.awUserStatus = data.home[0].body.awUserStatus;
                        // if(this.rmsRole === 'role-nbr-admin') {
                        //     this.router.navigate(['application-list']);
                        // }
                        // else if(this.rmsRole === 'role-operator') {
                        //     this.router.navigate(['application-customs-list']);
                        // }
                    }
                });
        }

        if (this.rmsRole === 'role-nbr-admin') {
            this.menuJson = [
                {
                    'topMenuId': 'Dashboard_TopMenu',
                    'leftMenuId': [
                        '/dashboard',
                    ]
                },
                {
                    'topMenuId': 'ApplicationList_TopMenu',
                    'icon': 'pi pi-list',
                    'leftMenuId': [
                        '/application-list'
                    ]
                },
                {
                    'topMenuId': 'আর্কাইভ',
                    'icon': 'pi pi-paperclip',
                    'leftMenuId': [
                        '/archive'
                    ]
                },
                {
                    'topMenuId': 'কী-বোর্ড নির্দেশনা',
                    'icon': 'pi pi-paperclip',
                    'leftMenuId': [
                        '/keyboard'
                    ]
                },
                
            ]
        } else if (this.rmsRole === 'role-sys-admin') {
            this.menuJson = [
                // {
                //     'topMenuId': 'Dashboard_TopMenu',
                //     'leftMenuId': [
                //         '/dashboard'
                //     ]
                // },               
                {
                    'topMenuId': 'OperatorList_TopMenu',
                    'icon': 'pi pi-paperclip',
                    'leftMenuId': [
                        '/operator-list'
                    ]
                },
                {
                    'topMenuId': 'CustomsHouseList_TopMenu',
                    'icon': 'pi pi-home',
                    'leftMenuId': [
                        '/customs-house-list'
                    ]
                },
                {
                    'topMenuId': 'পাসওয়ার্ড রিসেট',
                    'icon': 'pi pi-home',
                    'leftMenuId': [
                        '/password-recovery'
                    ]
                },
                {
                    'topMenuId': 'কী-বোর্ড নির্দেশনা',
                    'icon': 'pi pi-paperclip',
                    'leftMenuId': [
                        '/keyboard'
                    ]
                },
            ]
        } else if (this.rmsRole === 'role-operator' || this.rmsRole === 'role-nbr-investigation') {
            this.menuJson = [
                // {
                //     'topMenuId': 'Dashboard_TopMenu',
                //     'icon': 'pi pi-paperclip',
                //     'leftMenuId': [
                //         '/dashboard'
                //     ]
                // },
                {
                    'topMenuId': 'Application_List_TopMenu',
                    'icon': 'pi pi-list',
                    'leftMenuId': [
                        '/application-customs-list'
                    ]
                },
                {
                    'topMenuId': 'আর্কাইভ',
                    'icon': 'pi pi-paperclip',
                    'leftMenuId': [
                        '/operatorArchive'
                    ]
                },
                {
                    'topMenuId': 'Application_TopMenu',
                    'icon': 'pi pi-paperclip',
                    'leftMenuId': [
                        '/application-customs-step-one'
                    ]
                },
                {
                    'topMenuId': 'কী-বোর্ড নির্দেশনা',
                    'icon': 'pi pi-paperclip',
                    'leftMenuId': [
                        '/keyboard'
                    ]
                },
            ]
        } else{
            this.router.navigate(['page-not-found']);
        }

        this.sharedService.disableSource.asObservable()
            .subscribe(data => {
                if (data !== undefined) {
                    this.disableSidebar = data;
                }
            });
    }

    ngAfterViewInit() {
        setTimeout(() => {
            this.setActiveLanguageLink();

            this.addPlugIns();
        });
    }

    addPlugIns() {
        $('.comment-scrollbar').mCustomScrollbar({
            axis: 'y',
            autoHideScrollbar: true,
            scrollbarPosition: 'outside',
            theme: 'light-1'
        });

        $('#metismenu').metisMenu();

        $('#sidebarCollapse').on('click', function () {
            $('#sidebar').toggleClass('active');
            $('#footer').toggleClass('active');
        });
        $('.sicker-menu').sticky({
            topSpacing: 0
        });
        $('#sidebarCollapse').on('click', function () {
            $('body').toggleClass('mini-navbar');
        });
        $(document).on('click', '.header-right-menu .dropdown-menu', function (e) {
            e.stopPropagation();
        });
        new WOW().init();
    }

    deactivateMyAccountsLink() {
        if (this.router.url !== this.defaultLink) {
            $('#myAccountsMenuLink').removeClass('active');
        }
    }

    changeLanguage(lan: string) {
        this.translate.use(lan);
        this.setActiveLanguageLink();
    }

    setActiveLanguageLink() {
        if (this.translate.currentLang === 'en') {
            $('#englishLangLink').addClass('active');
            $('#banglaLangLink').removeClass('active');
            document.documentElement.setAttribute('lang', 'en');
        } else if (this.translate.currentLang === 'bn') {
            $('#banglaLangLink').addClass('active');
            $('#englishLangLink').removeClass('active');
            document.documentElement.setAttribute('lang', 'bn');
        }
    }
}
