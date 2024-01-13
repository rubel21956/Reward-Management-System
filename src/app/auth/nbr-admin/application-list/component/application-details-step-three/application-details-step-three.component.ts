import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {NbrApplicationService} from '@app/auth/nbr-admin/services/nbr-application.service';
import {ApplicationEntity} from '@app/auth/nbr-admin/model/applicationEntity';
import {MessageService} from 'primeng/api';
import { saveAs }  from 'file-saver';
import { SharedService } from '@app/common/services/shared.service';

@Component({
    selector: 'app-application-details-step-three',
    templateUrl: './application-details-step-three.component.html',
    styleUrls: ['./application-details-step-three.component.css']
})
export class ApplicationDetailsStepThreeComponent implements OnInit {

    applicationDetailsStepThreeGroup: FormGroup;
    public isLoading: boolean = false;
    applicationDetails: ApplicationEntity;
    applicationDetails1: ApplicationEntity;
    applicationDetails2: ApplicationEntity;
    applicationDetailsForApplicationDate: ApplicationEntity;
    currentDate = new Date();
    public rmsRole = '';
    public tableData: any = [];

    constructor(private activateRoute: ActivatedRoute, private nbrApplicationService: NbrApplicationService,
                private messageService: MessageService, private applicationDetailsStepThree: FormBuilder, 
                private sharedService: SharedService, private router: Router) {
    }


    ngOnInit(): void {
        this.rmsRole = this.sharedService.getRmsRole();
        if(this.rmsRole !== 'role-nbr-admin'){
            this.router.navigate(['page-not-found']);
        }else{
        this.applicationDetailsStepThreeGroup = this.applicationDetailsStepThree.group({}
        );
        this.getApplicationDetails(this.activateRoute.snapshot.params['oid']);
        }
    }

    getApplicationDetails(oid: string) {
        this.isLoading = true;
        this.nbrApplicationService.getApplicationDetails(oid).subscribe(res => {
                if (res.status === 200) {
                    this.applicationDetails = res.body.applicationCustomsStepOneDto;


            //temporary array for rearrange list 
          var ar = [];
          for (let dataIndex = 0; dataIndex < this.applicationDetails.rewardAmounts.length; dataIndex++) {
            var employeeData = this.applicationDetails.rewardAmounts[dataIndex];                        
            if(employeeData != undefined){              
                this.applicationDetails.rewardAmounts.forEach((entry)=>{
                  if(entry.sl == dataIndex.toString()){                                     
                    ar.push(entry);                                                                 
                  }
                });           
              }                                  
          }
          //for place nbrwelfare group in last 
          this.applicationDetails.rewardAmounts.forEach((entry)=>{
            if(entry.employeeName == 'জারাবো এর কল্যাণ তহবিল'){
              ar.push(entry);
            }
          });
        //for place nbrwelfare group in last 
          this.applicationDetails.rewardAmounts.forEach((entry)=>{
            if(entry.employeeName == 'কাস্টমস হাউস এর কল্যাণ তহবিল'){
              ar.push(entry);
            }
          });

          //for place informar group in fast place 
          this.applicationDetails.rewardAmounts.forEach((entry)=>{
            if(entry.employeeName == 'তথ্য সরবরাহকারী'){
              ar.unshift(entry);
            }
          });


          this.applicationDetails.rewardAmounts = ar;
                    this.applicationDetails.rewardAmounts.forEach(j => {  
                        let check = String(j.isRewarded);                                                               
                        if(check == 'true'){                           
                            this.tableData.push(j);
                        }
                    });
                                                      
                    this.applicationDetailsForApplicationDate = res.body.applicationCustomsStepTwoDto                                                           
                    this.applicationDetails.totalRewardedAmount = res.body.applicationNbrAdminStepThreeDto.totalRewardedAmount;
                    this.applicationDetails.rewardAmounts[0].isRewarded;
                    this.applicationDetails1 = res.body.applicationNbrAdminStepOneDto;
                    this.applicationDetails2 = res.body.applicationNbrAdminStepTwoDto;
                }
            },
            err => {
                this.isLoading = false;
                if (err.error && err.error.message) {
                    this.messageService.add({severity: 'error', summary: err.error.message, detail: ''});
                }
            }, ()=>{this.isLoading = false});
    }

    prevToApplicationDetailsStepTwoAccepted() {
        this.router.navigate(['../../nbr-admin-step-two-accepted',
            this.activateRoute.snapshot.params['oid']], {relativeTo: this.activateRoute})
    }

    submitApplicationDetailsStepThree() {
        this.isLoading = true;
        this.nbrApplicationService.updateFinalApprovalByNbr(this.activateRoute.snapshot.params['oid']).subscribe(res => {           
            if (res.oid === this.activateRoute.snapshot.params['oid']) {
                    this.messageService.add({severity: 'success', summary: 'আবেদনটি সফলভাবে গৃহীত হয়েছে', detail: ''});
                    setTimeout(() => {
                        this.router.navigate([`application-list`])
                    }, 3000);
                }
            },
            err => {
                this.isLoading = false;
                if (err.error && err.error.message) {
                    this.messageService.add({severity: 'error', summary: err.error.message, detail: ''});
                }
            });
    }

    submitApplicationDetailsStepThreeforSattalement() {
      this.isLoading = true;
      this.nbrApplicationService.updateFinalApprovalByNbrForSattlement(this.activateRoute.snapshot.params['oid']).subscribe(res => {           
          if (res.oid === this.activateRoute.snapshot.params['oid']) {
                  this.messageService.add({severity: 'success', summary: 'আবেদনটি সফলভাবে আর্কাইভে জমা হয়েছে', detail: ''});
                  setTimeout(() => {
                      this.router.navigate([`application-list`])
                  }, 3000);
              }
          },
          err => {
              this.isLoading = false;
              if (err.error && err.error.message) {
                  this.messageService.add({severity: 'error', summary: err.error.message, detail: ''});
              }
          });
  }

    private pdfData: any;
    printPdf() {
      const pdfUrl = URL.createObjectURL(this.pdfData);
      const popupWin = window.open(pdfUrl, '_blank', 'fullscreen=yes');
      // setTimeout(() => {
      //   popupWin?.print();
      // }, 1000);
    }
  

    printPreviewApplicationDetailsStepThree() {
       this.isLoading = true;
        this.nbrApplicationService.applicationStepThreeAccepted(this.activateRoute.snapshot.paramMap.get('oid')).subscribe(res => {
            if (res.status === 200) {
                // saveAs(res.body);
                this.pdfData = res.body;
                this.printPdf();
                this.isLoading = false;
                }
            },
            err => {
                this.isLoading = false;
                if (err.error && err.error.message) {
                    this.messageService.add({severity: 'error', summary: 'Data not found', detail: ''});
                }
            },
            () => {
                this.isLoading = false;
            });
        setTimeout(() => {
        }, 3000);
    }

    getCurrentFiscalYear(): string {
      // Get the current date
      const currentDate = new Date();
  
      // Define the start month of your fiscal year (e.g., April)
      const fiscalYearStartMonth = 4;
  
      // Calculate the fiscal year based on the current date
      const currentYear = currentDate.getFullYear();
      const currentMonth = currentDate.getMonth() + 1; // January is 0
  
      let fiscalYearStartYear: number;
  
      if (currentMonth < fiscalYearStartMonth) {
        // If the current month is before the start month, subtract 1 from the year
        fiscalYearStartYear = currentYear - 1;
      } else {
        fiscalYearStartYear = currentYear;
      }
  
      const fiscalYearEndYear = fiscalYearStartYear + 1;
  
      // Format the fiscal year as a string
      const fiscalYearString = `${fiscalYearStartYear}-${fiscalYearEndYear}`;
  
      return fiscalYearString;
    }
}
