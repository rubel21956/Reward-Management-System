import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { NbrApplicationService } from '../services/nbr-application.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedService } from '@app/common/services/shared.service';
import { TranslateService } from '@ngx-translate/core';
import { PrimeNGConfig } from 'primeng/api';

@Component({
    selector: 'app-application-list',
    templateUrl: './application-list.component.html',
    styleUrls: ['./application-list.component.css']
})
export class ApplicationListComponent implements OnInit {

    public isLoading: boolean = false;
    minDate: Date;
    maxDate: Date;
    applicationListGroup: FormGroup;
    offices: DropdownModule;
    public sessionList: any[];
    public tempSessionList: any[];
    applicationStatus: DropdownModule;
    officeNames: DropdownModule;
    applicationDate: DropdownModule;
    public rmsRole = '';


    constructor(private applicationList: FormBuilder, private nbrApplicationService: NbrApplicationService,
        private router: Router, private activateRoute: ActivatedRoute, private sharedService: SharedService,
        private config: PrimeNGConfig, private translateService: TranslateService) {

        const today = new Date();
        this.minDate = new Date('January 01, 1900');
        this.maxDate = new Date(today);

        this.offices = [
            // {name: '100'},
            { name: 'কাস্টম হাউস, ঢাকা', value: '101' },
            { name: 'কাস্টম হাউস, বেনাপোল', value: '601' },
            { name: 'কাস্টম হাউস, চট্টগ্রাম', value: '301' },
            { name: 'কাস্টম হাউস, মোংলা', value: '501' },
            { name: 'কাস্টম হাউস, পানগাঁও', value: '752' },
            { name: 'কাস্টম হাউস, আইসিডি', value: '102' },
            { name: 'কাস্টমস গোয়েন্দা ও তদন্ত অধিদপ্তর', value: '302' },
            { name: 'কাস্টমস বন্ড কমিশনারেট, ঢাকা (উত্তর)', value: '981' },
            { name: 'কাস্টমস বন্ড কমিশনারেট, ঢাকা (দক্ষিণ)', value: '982' },
            { name: 'কাস্টমস বন্ড কমিশনারেট, চট্টগ্রাম', value: '985' },

        ];
        this.applicationStatus = [
            { name: 'সকল আবেদন', value: 'All' },
            { name: 'কার্যক্রম চলমান', value: 'Submitted' },
            { name: 'মঞ্জুরীর জন্য সুপারিশকৃত', value: 'Accepted' },
            { name: 'বিবেচনাযোগ্য নয়', value: 'Rejected' },
            { name: 'ফেরতকৃত', value: 'Returned' },
        ];
        this.applicationDate = [
        ];

        let date = new Date().getFullYear();

        for (let i = 2001; i <= date; i++) {
            this.a.push({ name: i.toString(), value: i.toString() });
        }
        this.a.reverse();
        this.applicationDate = this.a;

    }
    public a: any = [];
    ngOnInit(): void {
        this.rmsRole = this.sharedService.getRmsRole();
        if (this.rmsRole !== 'role-nbr-admin') {
            this.router.navigate(['page-not-found']);
        } else {
            this.applicationListGroup = this.applicationList.group({
                applyingOfficeOid: [''],
                applyingYearOid: [''],
                applicationStatus: [''],
                officeNames: [''],
                applicationDate: [''],
            }
            );
            this.getApplicantList();
        }
        this.translate('bn');
    }

    viewDetails(i: any) {
        this.router.navigate(['nbr-admin-step-one', this.sessionList[i].applicationCustomsStepOneDto['oid']], { relativeTo: this.activateRoute })
    }

    getApplicantList(): void {
        this.isLoading = true;
        this.nbrApplicationService.getApplicationList().subscribe(res => {
            if (res.status === 200) {
                this.sessionList = res.body
                this.sessionList.reverse();
            }
        },
            err => {
                this.isLoading = false;
                if (err.status === 404) {
                    this.sessionList = [];
                }
                if (err.error && err.error.message) {
                }
            },
            () => {
                this.isLoading = false;
            });
    }

    searchFromApplicationList(): void {
        this.isLoading = true;
        let officeNames = (this.applicationListGroup.value.officeNames == null ? '' : this.applicationListGroup.value.officeNames);
        let applicationDate = (this.applicationListGroup.value.applicationDate == null ? '' : this.applicationListGroup.value.applicationDate);
        let applicationStatus = (this.applicationListGroup.value.applicationStatus == null ? '' : this.applicationListGroup.value.applicationStatus);
        console.log(officeNames);
        console.log(applicationDate);
        console.log(applicationStatus);

        this.nbrApplicationService.getStatusAndOfficeWiseApplicationList(applicationStatus, officeNames, applicationDate).subscribe(
            (res) => {
                this.sessionList = res.body;
            },
            (err) => { },
            () => {
                this.isLoading = false;
            }
        )


    }

    printPreviewApplicationList() {
        window.print();
    }

    translate(lang: string) {
        this.translateService.use(lang);
        this.translateService.get('primeng').subscribe(res => this.config.setTranslation(res));
    }
}
