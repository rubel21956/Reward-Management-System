import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NbrApplicationService} from '@app/auth/nbr-admin/services/nbr-application.service';
import {ActivatedRoute, Router} from '@angular/router';
import {MessageService, PrimeNGConfig} from 'primeng/api';
import {ApplicationEntity} from '@app/auth/nbr-admin/model/applicationEntity';
import {UtilityService} from '@app/common/services/util/utility.service';
import { saveAs }  from 'file-saver';
import { ApplicationCustomsStepFive } from '@app/auth/nbr-admin/model/applicationCustomsStepFive';
import { ApplicationCustomsStepTwo } from '@app/auth/nbr-admin/model/applicationCustomsStepTwo';
import { SharedService } from '@app/common/services/shared.service';

import { TranslateService } from '@ngx-translate/core';
import { DateFormat } from '@app/common/pipes/date-format.pipe';
import { DatePipe, formatDate } from '@angular/common';
import { ApplicationAttachment } from '@app/auth/application/model/applicationAttachment';
import { ApplicationEntity2 } from '@app/auth/nbr-admin/model/applicationEntity2';

@Component({
  selector: "app-application-details-step-one",
  templateUrl: "./application-details-step-one.component.html",
  styleUrls: ["./application-details-step-one.component.css"],
})
export class ApplicationDetailsStepOneComponent implements OnInit {
  index: number = 0;
  public isLoading: boolean = false;
  applicationDetailsStepOneGroup: FormGroup;
  applicationDetails: ApplicationEntity;
  applicationDetails1: ApplicationEntity;
  applicationDetails2: ApplicationEntity;
  rewardAmounts: any[];
  rewardAmountsInBangla: any;
  attachmentStepFive: ApplicationCustomsStepFive;
  attachmentStepTwo: ApplicationAttachment[] = []; 
  public rmsRole = "";

  constructor(
    private config: PrimeNGConfig, 
    private translateService: TranslateService,
    private applicationDetailsStepOne: FormBuilder,
    private nbrApplicationService: NbrApplicationService,
    private router: Router,
    private messageService: MessageService,
    private activateRoute: ActivatedRoute,
    private utilityService: UtilityService,
    private sharedService: SharedService
  ) {
    this.translate("bn");
  }

  get f() {
    return this.applicationDetailsStepOneGroup.controls;
  }

  ngOnInit(): void {
    this.rmsRole = this.sharedService.getRmsRole();
    if (this.rmsRole !== "role-nbr-admin") {
      this.router.navigate(["page-not-found"]);
    } else {
      //this.rewardAmountsInBangla = this.utililyService.getDigitBanglaFromEnglish
      this.applicationDetailsStepOneGroup =
        this.applicationDetailsStepOne.group({
          nbrRewardFileNumber: ["", Validators.required],
          customsAwardSanctioningCommittee: [""],
          oid: [this.activateRoute.snapshot.params["oid"]],
        });
      this.getApplicationDetails(this.activateRoute.snapshot.params["oid"]);
      // this.getBanglaDigitFromEnglish('5');
    }
    
    
  }

  private pdfData: any;
  printPdf() {
    const pdfUrl = URL.createObjectURL(this.pdfData);
    const popupWin = window.open(pdfUrl, '_blank', 'fullscreen=yes');
    // setTimeout(() => {
    //   popupWin?.print();
    // }, 1000);
  }

  getStepFiveFile(event) {
    let buttonId = event.currentTarget.id;
    let serviceParam = this.attachmentStepFive?.fileName;
    console.log(serviceParam);
    
    let fileName = this.attachmentStepFive?.attachmentType ;
    this.nbrApplicationService.getStepFiveFileDownload(serviceParam).subscribe(
      (res) => {
        console.log(res.status);        
        if (res.status === 200) {                   
          this.pdfData = res.body;
          this.printPdf();
        }
       
      },
      (err) => {
        console.log('error');
        this.isLoading = false;
        if (err.status === 404) {
        }
        if (err.error && err.error.message) {
          this.messageService.add({
            severity: "error",
            summary: "Data not found",
            detail: "",
          });
        }
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  downloadStepTwo(i: any) {
    let serviceParam = this.attachmentStepTwo[i]?.fileName;
    let attachmentType = this.attachmentStepTwo[i]?.attachmentType;

    if(attachmentType ==  "এজেন্টদের ঘোষণা মোতাবেক বিল অফ এন্ট্রি"){
      this.nbrApplicationService.getStepOneFileDownload(serviceParam).subscribe(
        (res) => {
          
          if (res.status === 200) {
           // saveAs(res.body, fileName);
           this.pdfData = res.body;
           this.printPdf();
          }
        },
        (err) => {
          this.isLoading = false;
          if (err.status === 404) {
          }
          if (err.error && err.error.message) {
            this.messageService.add({
              severity: "error",
              summary: "Data not found",
              detail: "",
            });
          }
        },
        () => {
          this.isLoading = false;
        }
      );
    }else if(attachmentType ==  "সাক্ষরিত আবেদনপত্র"){
      this.nbrApplicationService.getStepFiveFileDownload(serviceParam).subscribe(
        (res) => {
          console.log(res.status);        
          if (res.status === 200) {                   
            this.pdfData = res.body;
            this.printPdf();
          }
         
        },
        (err) => {
          console.log('error');
          this.isLoading = false;
          if (err.status === 404) {
          }
          if (err.error && err.error.message) {
            this.messageService.add({
              severity: "error",
              summary: "Data not found",
              detail: "",
            });
          }
        },
        () => {
          this.isLoading = false;
        }
      );
    }else{
      this.nbrApplicationService.getStepTwoFileDownload(serviceParam).subscribe(
        (res) => {          
          if (res.status === 200) {
           // saveAs(res.body, fileName);
           this.pdfData = res.body;
           this.printPdf();
          }
        },
        (err) => {
          this.isLoading = false;
          if (err.status === 404) {
          }
          if (err.error && err.error.message) {
            this.messageService.add({
              severity: "error",
              summary: "Data not found",
              detail: "",
            });
          }
        },
        () => {
          this.isLoading = false;
        }
      );
    }
    
   
  }

  getApplicationDetails(oid: string) {
    this.isLoading = true;
    this.nbrApplicationService.getApplicationDetails(oid).subscribe(
      (res) => {
        if (res.status === 200) {         
          this.applicationDetails = res.body.applicationCustomsStepOneDto;
          this.applicationDetails.totalAppliedAmount = 0; 
          console.log(this.applicationDetails.rewardAmounts);
          
          this.applicationDetails.rewardAmounts.forEach((e) => {
            this.applicationDetails.totalAppliedAmount += e.appliedRewardAmount;
          });         
          console.log(this.applicationDetails);              
          this.applicationDetails1 = res.body.applicationNbrAdminStepOneDto;
          this.applicationDetails2 = res.body.applicationNbrAdminStepTwoDto;
          this.setFormValue();
          
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

       
          this.applicationDetails.rewardableAmount = this.applicationDetails.rewardableAmount;        
          this.attachmentStepFive =
            res.body.applicationCustomsStepFiveDto.applicationAttachmentEntity;
            this.attachmentStepTwo = 
            res.body.applicationCustomsStepTwoDto.applicationAttachments;                    
            if(res.body.applicationCustomsStepOneDto.applicationAttachments.length){             
              this.attachmentStepTwo.unshift(res.body.applicationCustomsStepOneDto.applicationAttachments[0])  ;  
            }

        }
      },
      
      (err) => {
        this.isLoading = false;
        if (err.error && err.error.message) {
          this.messageService.add({
            severity: "error",
            summary: err.error.message,
            detail: "",
          });
        }
      },
      ()=>{
        this.isLoading = false;
      }
    );
  }

  setFormValue() {
    this.applicationDetailsStepOneGroup.patchValue({
      nbrRewardFileNumber: this.applicationDetails.nbrRewardFileNumber,
      customsAwardSanctioningCommittee: this.applicationDetails1.customsAwardSanctioningCommittee ? new Date(this.utilityService.getEnglishDigitFromBangla(this.applicationDetails1.customsAwardSanctioningCommittee)) : null
    });     
  }


  nextPageToApplicationDetailsStepTwo() {
    this.isLoading = true;
      if(this.applicationDetailsStepOneGroup.get("nbrRewardFileNumber").value == ''){
        this.messageService.add({
          severity: "error",
          summary: "অনুগ্রহ করে রাজস্ব বোর্ডের নথি নং প্রদান করুন।",
          detail: "পরবর্তী ধাপ",
      });  
      return;
    }else if(this.applicationDetailsStepOneGroup.get("customsAwardSanctioningCommittee").value == null){
      this.messageService.add({
        severity: "error",
        summary: "অনুগ্রহ করে শুল্ক পুরস্কার মঞ্জুরি কমিটির সভার তারিখ প্রদান করুন।",
        detail: "পরবর্তী ধাপ",
    });  
    return;
    }
    if (this.applicationDetailsStepOneGroup.valid) {      
          this.nbrApplicationService
        .updateApplicationCustomsStepOneUsingPUT(
          this.applicationDetailsStepOneGroup.value,
          this.activateRoute.snapshot.params["oid"]
        )
        .subscribe(
          (res) => {
            if (res.status === 200) {
              this.messageService.add({
                severity: "success",
                summary: "ধাপ-১ সফল ভাবে সম্পন্ন হয়েছে",
                detail: "",
              });
              setTimeout(() => {
                this.isLoading = true
                this.router.navigate(
                  [
                    "../../nbr-admin-step-two",
                    this.activateRoute.snapshot.params["oid"],
                  ],
                  { relativeTo: this.activateRoute }
                );
              }, 2000);
            }
          },
          (err) => {
            this.isLoading = false;
            if (err.error && err.error.message) {
              this.messageService.add({
                severity: "error",
                summary: err.error.message,
                detail: "",
              });
            }
          },
          ()=>{}
        );
    } else {
      this.isLoading = false;
      this.messageService.add({
        severity: "error",
        summary: "অনুগ্রহ পূর্বক প্রয়োজনীয় তথ্য প্রদান করুন",
        detail: "",
      });
    }
  }

  prevToApplicationList() {
    this.router.navigate(["application-list"]);
  }

  forwardToApplicationDetailsStepTwo() {
    this.router.navigate(
      [
        `../../nbr-admin-step-two/${this.activateRoute.snapshot.paramMap.get(
          "oid"
        )}`,
      ],
      { relativeTo: this.activateRoute }
    );
  }

 
  // Modified by Arif............................
  
  printPreviewApplicationDetailsStepOne() {
    window.print();
  }

  downloadApplicationDetailsOne(){
     this.isLoading = false;
     if(this.applicationDetailsStepOneGroup.get("nbrRewardFileNumber").value == ''){
      this.messageService.add({
        severity: "error",
        summary: "অনুগ্রহ করে রাজস্ব বোর্ডের নথি নং প্রদান করুন।",
        detail: "পরবর্তী ধাপ",
    }); 
    this.isLoading = false; 
    return;
  }
    if (this.applicationDetailsStepOneGroup.valid) {
      this.isLoading = true;      
      this.nbrApplicationService
        .updateApplicationCustomsStepOneUsingPUT(
          this.applicationDetailsStepOneGroup.value,
          this.activateRoute.snapshot.params["oid"]
        )
        .subscribe(
          (res) => {
            if (res.status === 200) {            
              this.nbrApplicationService
                .applicationStepOneView(
                  this.activateRoute.snapshot.paramMap.get("oid")
                )
                .subscribe(
                  (res) => {
                    if (res.status === 200) {
                      // saveAs(res.body);
                      this.pdfData = res.body;
                      this.printPdf();
                    }
                  },
                  (err) => {
                    this.isLoading = false;
                    if (err.error && err.error.message) {
                      this.messageService.add({
                        severity: "error",
                        summary: "Data not found",
                        detail: "",
                      });
                    }
                  },
                  () => {
                    this.isLoading = false;
                  }
                );
              setTimeout(() => {}, 5000);
            }
          },
          (err) => {
            this.isLoading = false;
            if (err.error && err.error.message) {
              this.messageService.add({
                severity: "error",
                summary: err.error.message,
                detail: "",
              });
            }
          }
        );
    } else {
      this.isLoading = false;
      this.messageService.add({
        severity: "error",
        summary: "অনুগ্রহ পূর্বক প্রয়োজনীয় তথ্য প্রদান করুন",
        detail: "",
      });
    }
  }

  translate(lang: string) {
    this.translateService.use(lang);
    this.translateService.get('primeng').subscribe(res => this.config.setTranslation(res));
  }

  convertToNumber() {
    console.log(" this is the below");
    
    console.log(this.applicationDetails);
      
  }

}
