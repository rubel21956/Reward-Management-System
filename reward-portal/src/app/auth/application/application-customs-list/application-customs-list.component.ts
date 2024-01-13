import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ApplicationService} from '@app/auth/application/services/application.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DropdownModule} from 'primeng/dropdown';
import { SharedService } from '@app/common/services/shared.service';

@Component({
    selector: 'app-application-customs-list',
    templateUrl: './application-customs-list.component.html',
    styleUrls: ['./application-customs-list.component.css']
})

export class ApplicationCustomsListComponent implements OnInit {

    public sessionList: any[];
    userId?: string;
    applicationCustomsListGroup: FormGroup;
    applicationStatus: DropdownModule[];
    public rmsRole = '';
    public isLoading = true;
    private sessionListArray = [];
    applicationDate: DropdownModule;
    currentOfficeName: string = '';

    constructor(private applicationService: ApplicationService, private activateRoute: ActivatedRoute,
                private applicationCustomsList: FormBuilder, private sharedService: SharedService,
                private router: Router) {
        this.applicationStatus = [
            {name: 'সকল আবেদন', value: 'All'},
            {name: 'খসড়া', value: 'Draft'},
            {name: 'কার্যক্রম চলমান', value: 'Submitted'},
            {name: 'মঞ্জুরীর জন্য সুপারিশকৃত', value: 'Accepted'},            
            {name: 'ফেরতকৃত', value: 'Returned'},
           
            
        ];
        this.applicationDate = [        
        ];   
        
        let date = new Date().getFullYear();

        for (let i = 2001; i <= date; i++) {          
            this.a.push({name: i.toString(), value: i.toString()});                       
        }
        this.a.reverse();                             
        this.applicationDate = this.a;                       
        
    }
    public a: any = [];
    

    ngOnInit(): void {
        this.rmsRole = this.sharedService.getRmsRole();
        this.currentOfficeName = this.sharedService.getCurrentOfficeName();
        if(this.rmsRole !== 'role-operator' && this.rmsRole !== 'role-nbr-investigation'){
            this.router.navigate(['page-not-found']);
        }else{
            this.userId = this.sharedService.getcurrentLoggedInUserID();
            this.applicationCustomsListGroup = this.applicationCustomsList.group({
                    applicationStatus: [''],
                    applicationDate: [''],
                }
            );
            this.searchApplicationCustomsList();
        }
    }

    getApplicantList(): void {
        this.applicationService.getOperatorWiseApplicationList(this.userId).subscribe(res => {            
                if (res.status === 200) {
                    this.sessionList = res.body;                                      
                    this.sessionListArray = res.body;                                     
                }
            },
            err => {
                if (err.status === 404) {
                    this.sessionList = [];
                }
                if (err.error && err.error.message) {
                }
                this.isLoading = false;
            },
            () => {
                this.isLoading=false;
            });
    }

    editDetails(i: any) {
        
        this.router.navigate(['../application-customs-step-one',
            this.sessionList[i].applicationCustomsStepOneDto['oid']], {relativeTo: this.activateRoute})
    }

    viewDetails(i: any) {
        this.router.navigate(['../application-customs-step-one',
            this.sessionList[i].applicationCustomsStepOneDto['oid']], {relativeTo: this.activateRoute})
    }

    searchApplicationCustomsList(): void {
        this.isLoading=true;
        let applicationStatus: string = this.applicationCustomsListGroup.value.applicationStatus == null ? '': this.applicationCustomsListGroup.value.applicationStatus;
        let userId: string = this.userId;
        let applicationDate: string  = this.applicationCustomsListGroup.value.applicationDate == null ? '' : this.applicationCustomsListGroup.value.applicationDate; 
        this.applicationService.getStatusWiseApplicationList(applicationStatus, applicationDate, userId).subscribe(res => {
                if (res.status === 200) {                                        
                    this.sessionList = res.body;
                    this.sessionList.reverse();
                }
            },
            err => {
                this.isLoading=false;
                if (err.status === 404) {
                    this.sessionList = [];
                }
                if (err.error && err.error.message) {
                }
            },
            () => {
                this.isLoading=false;
            });
       
    } 
}
