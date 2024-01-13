import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedService } from '@app/common/services/shared.service';
import { DropdownModule } from 'primeng/dropdown';
import { ApplicationService } from '../../services/application.service';

@Component({
  selector: 'app-archive',
  templateUrl: './archive.component.html',
  styleUrls: ['./archive.component.css']
})
export class ArchiveComponent implements OnInit {

  public sessionList: any[];
    userId?: string;
    applicationCustomsListGroup: FormGroup;
    applicationStatus: DropdownModule[];
    public rmsRole = '';
    public isLoading = true;
    private sessionListArray = [];
    applicationDate: DropdownModule;
    currentOfficeName: string = '';
    applicationStatusForArchive: string= 'মীমাংসিত';

    constructor(private applicationService: ApplicationService, private activateRoute: ActivatedRoute,
                private applicationCustomsList: FormBuilder, private sharedService: SharedService,
                private router: Router) {
        this.applicationStatus = [
            {name: 'মীমাংসিত', value: 'Sattlement'},
            {name: 'বিবেচনাযোগ্য নয়', value: 'Rejected'},
                       
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
            this.getApplicantList();
        }
    }

    getApplicantList(): void {
        this.applicationService.getOperatorWiseApplicationListofSattlement(this.userId).subscribe(res => {            
                if (res.status === 200) {
                    this.sessionList = res.body;
                    console.log(this.sessionList);
                    
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
        let applicationStatus: string = this.applicationCustomsListGroup.value.applicationStatus == null ? '': this.applicationCustomsListGroup.value.applicationStatus;
        let userId: string = this.userId;
        let applicationDate: string  = this.applicationCustomsListGroup.value.applicationDate == null ? '' : this.applicationCustomsListGroup.value.applicationDate; 
        this.applicationService.getStatusWiseApplicationListOfSattlement(applicationStatus, applicationDate, userId).subscribe(res => {
                if (res.status === 200) {                   
                    this.applicationStatusForArchive = applicationStatus == 'Sattlement' ? 'মীমাংসিত':'বিবেচনাযোগ্য নয়';
                    this.sessionList = res.body;
                    this.sessionList.reverse();
                }
            },
            err => {
                if (err.status === 404) {
                    this.sessionList = [];
                }
                if (err.error && err.error.message) {
                }
            },
            () => {
            });
       
    } 

    rejectedApplication(){
    this.router.navigate(['rejectedApplication']);
    }

}
