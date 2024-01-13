import {Component, OnInit} from '@angular/core';
import {DropdownModule} from 'primeng/dropdown';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NbrApplicationService} from '@app/auth/nbr-admin/services/nbr-application.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ApplicationEntity} from '@app/auth/nbr-admin/model/applicationEntity';
import {MessageService} from 'primeng/api';
import { UtilityService } from '@app/common/services/util/utility.service';
import { saveAs }  from 'file-saver';
import { SharedService } from '@app/common/services/shared.service';

@Component({
  selector: "app-application-details-step-two",
  templateUrl: "./application-details-step-two.component.html",
  styleUrls: ["./application-details-step-two.component.css"],
})
export class ApplicationDetailsStepTwoComponent implements OnInit {
  tempList: DropdownModule;
  temp1List: DropdownModule;
  public isLoading: boolean = false;
  applicationDetailsStepTwoGroup: FormGroup;
  applicationDetails: ApplicationEntity;
  applicationDetails2: ApplicationEntity;
  temp: ApplicationEntity;
  public rewardPercentage: any = [];
  public tmp: any = [];
  public rmsRole = "";
  pdfData: any;

  constructor(
    private applicationDetailsStepTwo: FormBuilder,
    private nbrApplicationService: NbrApplicationService,
    private router: Router,
    private activateRoute: ActivatedRoute,
    private messageService: MessageService,
    private utilityService: UtilityService,
    private sharedService: SharedService
  ) {
    this.tempList = [
      { bnname: "আবেদন বিবেচনাযোগ্য", value: "আবেদন বিবেচনাযোগ্য" },
      { bnname: "আবেদন বিবেচনাযোগ্য নয়", value: "আবেদন বিবেচনাযোগ্য নয়" },
      {
        bnname: "বিবেচনার জন্য আরও কাগজ পত্রের প্রয়োজন", value: "বিবেচনার জন্য প্রয়োজনীয় নথি প্রয়োজন",
      },
    ];
    this.temp1List = [
      { name: "টাকার পরিমাণ (অংকে)" },
      { name: "টাকার পরিমাণ (শতাংশ)" },
    ];
  }

  get f() {
    return this.applicationDetailsStepTwoGroup.controls;
  }

  ngOnInit(): void {
    this.rmsRole = this.sharedService.getRmsRole();
    if (this.rmsRole !== "role-nbr-admin") {
      this.router.navigate(["page-not-found"]);
    } else {
      this.applicationDetailsStepTwoGroup =
        this.applicationDetailsStepTwo.group({
          officeName: [""],
          suggestionFromNbr: [""],
          suggestionStatus: [""],
          extraReceivedAmount: [""],
          takaUnit: [""],
          isRewarded: [""],
          applicationStatus: [""],
          applicationStatusBn: [""],
        });
      this.getApplicationDetails(this.activateRoute.snapshot.params["oid"]);
    }
  }

  nextToApplicationDetailsStepTwoAccepted() {    
    this.isLoading = true;
    if(this.f.suggestionStatus.value == '' || this.f.suggestionStatus.value == null){
      this.messageService.add({
        severity: "error",
        summary: "অনুগ্রহ করে সুপারিশ সমূহ সিলেক্ট  করুন।",
        detail: "",
      });
      this.isLoading = false;
      return;
    }
    if (this.applicationDetailsStepTwoGroup.valid) {
      this.isLoading = true;
      const requestBody = {
        suggestionFromNbr:
          this.applicationDetailsStepTwoGroup.value.suggestionFromNbr,
        suggestionStatus:
          this.applicationDetailsStepTwoGroup.value.suggestionStatus,
        applicationStatus: this.applicationDetails2?.applicationStatus === 'Sattlement' ? 'Sattlement': 'Submitted',
        applicationStatusBn: this.applicationDetails2?.applicationStatus === 'Sattlement' ? 'মীমাংসিত': "কার্যক্রম চলমান",
      };
      this.nbrApplicationService
        .updateApplicationCustomsStepTwoUsingPUT(
          requestBody,
          this.activateRoute.snapshot.params["oid"]
        )
        .subscribe(
          (res) => {            
            if (res.status === 200) {
              this.isLoading = true;
              this.messageService.add({
                severity: "success",
                summary: "ধাপ-২ সফল ভাবে সম্পন্ন হয়েছে",
                detail: "",
              });
              setTimeout(() => {              
                this.router.navigate(
                  [
                    "../../nbr-admin-step-two-accepted",
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
          ()=>{
            // this.isLoading = false;
          }
        )
    } else {
      this.isLoading = false;
      this.messageService.add({
        severity: "error",
        summary: "অনুগ্রহ পূর্বক প্রয়োজনীয় তথ্য প্রদান করুন",
        detail: "",
      });
    }
  }

  submitApplicationDetailsStepTwo() {
    if (
      this.applicationDetailsStepTwoGroup.value.suggestionStatus ===
      "আবেদন বিবেচনাযোগ্য নয়"
    ) {
      this.applicationDetailsStepTwoGroup.value.applicationStatus = "Rejected";
      this.applicationDetailsStepTwoGroup.value.applicationStatusBn =
        "বিবেচনাযোগ্য নয়";
    } else {
      this.applicationDetailsStepTwoGroup.value.applicationStatus = "Returned";
      this.applicationDetailsStepTwoGroup.value.applicationStatusBn = "ফেরতকৃত";
    }
    if (this.applicationDetailsStepTwoGroup.valid) {
      const requestBody = {
        suggestionFromNbr:
          this.applicationDetailsStepTwoGroup.value.suggestionFromNbr,
        suggestionStatus:
          this.applicationDetailsStepTwoGroup.value.suggestionStatus,
        applicationStatus:
          this.applicationDetailsStepTwoGroup.value.applicationStatus,
        applicationStatusBn:
          this.applicationDetailsStepTwoGroup.value.applicationStatusBn,
      };
      this.nbrApplicationService
        .updateApplicationCustomsStepTwoUsingPUT(
          requestBody,
          this.activateRoute.snapshot.params["oid"]
        )
        .subscribe(         
          (res) => {
            if (res.status === 200) {
              this.isLoading = true;
              this.messageService.add({
                severity: "success",
                summary: "প্রার্থীর নিকট আবেদনটি প্রেরণ করা হয়েছে",
                detail: "",
              });
              setTimeout(() => {
                this.router.navigate(["application-list"]);
              }, 3000);
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
    } else {
      this.isLoading = false;
      this.messageService.add({
        severity: "error",
        summary: "অনুগ্রহ পূর্বক প্রয়োজনীয় তথ্য প্রদান করুন",
        detail: "",
      });
    }
  }

  printPreviewRejectedOfApplicationDetailsStepTwo() {
    if (
      this.applicationDetailsStepTwoGroup.value.suggestionStatus ===
      "আবেদন বিবেচনাযোগ্য নয়"
    ) {
      this.applicationDetailsStepTwoGroup.value.applicationStatus = "Rejected";
      this.applicationDetailsStepTwoGroup.value.applicationStatusBn =
        "বিবেচনাযোগ্য নয়";
    } else {
      this.applicationDetailsStepTwoGroup.value.applicationStatus = "Returned";
      this.applicationDetailsStepTwoGroup.value.applicationStatusBn = "ফেরতকৃত";
    }
    if (this.applicationDetailsStepTwoGroup.valid) {
      const requestBody = {
        suggestionFromNbr:
          this.applicationDetailsStepTwoGroup.value.suggestionFromNbr,
        suggestionStatus:
          this.applicationDetailsStepTwoGroup.value.suggestionStatus,
        applicationStatus:
          this.applicationDetailsStepTwoGroup.value.applicationStatus,
        applicationStatusBn:
          this.applicationDetailsStepTwoGroup.value.applicationStatusBn,
      };
      this.nbrApplicationService
        .updateApplicationCustomsStepTwoUsingPUT(
          requestBody,
          this.activateRoute.snapshot.params["oid"]
        )
        .subscribe(
          (res) => {
            this.isLoading = true;
            if (res.status === 200) {
              // this.messageService.add({
              //   severity: "success",
              //   summary: "প্রার্থীর নিকট আবেদনটি প্রেরণ করা হয়েছে",
              //   detail: "",
              // });
              this.nbrApplicationService
                .applicationStepTwoView(
                  this.activateRoute.snapshot.paramMap.get("oid")
                )
                .subscribe(
                  (res) => {
                    this.isLoading = true;
                    if (res.status === 200) {
                      // saveAs(res.body);
                      this.pdfData=res.body;
                      this.printPdf();
                      this.isLoading = false;
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
                   
                  }
                );
              setTimeout(() => {
                this.router.navigate(["application-list"]);
              }, 5000);
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

  printPreviewAcceptedOfApplicationDetailsStepTwo() {
    if (this.applicationDetailsStepTwoGroup.valid) {
      this.isLoading = true;
      const requestBody = {
        suggestionFromNbr:
          this.applicationDetailsStepTwoGroup.value.suggestionFromNbr,
        suggestionStatus:
          this.applicationDetailsStepTwoGroup.value.suggestionStatus,
        applicationStatus: "Submitted",
        applicationStatusBn: "কার্যক্রম চলমান",
      };
      this.nbrApplicationService
        .updateApplicationCustomsStepTwoUsingPUT(
          requestBody,
          this.activateRoute.snapshot.params["oid"]
        )
        .subscribe(
          (res) => {
            this.isLoading = true;
            if (res.status === 200) {
              // this.messageService.add({
              //   severity: "success",
              //   summary: "ধাপ-২ সফল ভাবে ডাউনলোড হয়েছে",
              //   detail: "",
              // });
              this.nbrApplicationService
                .applicationStepTwoView(
                  this.activateRoute.snapshot.paramMap.get("oid")
                )
                .subscribe(
                  (res) => {
                    this.isLoading = true;
                    if (res.status === 200) {
                      // saveAs(res.body);
                      this.pdfData = res.body;
                      this.printPdf();
                      this.isLoading = false;
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
          },
          ()=>{
            
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

  printPdf() {
    const pdfUrl = URL.createObjectURL(this.pdfData);
    const popupWin = window.open(pdfUrl, '_blank', 'fullscreen=yes');
    // setTimeout(() => {
    //   popupWin?.print();
    // }, 1000);
  }

  prevToApplicationDetailsStepOne() {
    this.router.navigate(
      ["../../nbr-admin-step-one", this.activateRoute.snapshot.params["oid"]],
      { relativeTo: this.activateRoute }
    );
  }

  convertEnglishToBangla(amountList) {
    let finalEnlishToBanglaNumber = {
      "0": "০",
      "1": "১",
      "2": "২",
      "3": "৩",
      "4": "৪",
      "5": "৫",
      "6": "৬",
      "7": "৭",
      "8": "৮",
      "9": "৯",
    };
    amountList.forEach(function (value) {
      // let finalEnlishToBanglaNumber = { '0': '০', '1': '১', '2': '২', '3': '৩', '4': '৪', '5': '৫', '6': '৬', '7': '৭', '8': '৮', '9': '৯' };
      for (let x in finalEnlishToBanglaNumber) {
        value.appliedRewardAmount = value.appliedRewardAmount
          .toString()
          .replace(new RegExp(x, "g"), finalEnlishToBanglaNumber[x]);
      }
    });
    for (let x in finalEnlishToBanglaNumber) {
      this.applicationDetails.rewardableAmount =
        this.applicationDetails.rewardableAmount
          .toString()
          .replace(new RegExp(x, "g"), finalEnlishToBanglaNumber[x]);
    }
  }

  getApplicationDetails(oid: string) {
    this.isLoading = true;
    this.nbrApplicationService.getApplicationDetails(oid).subscribe(
      (res) => {
        if (res.status === 200) {
          this.applicationDetails = res.body.applicationCustomsStepOneDto;
          this.applicationDetails2 = res.body.applicationNbrAdminStepTwoDto;
          this.temp = res.body.applicationNbrAdminStepTwoDto;
          this.applicationDetails.totalAppliedAmount = 0; 
          this.applicationDetails.rewardAmounts.forEach((e) => {
            this.applicationDetails.totalAppliedAmount += e.appliedRewardAmount;
            
            
          });  
          this.setFormValue();
          this.calculatePercentage();
          // this.utilityService.convertEnglishToBangla(
          //   this.applicationDetails.rewardAmounts
          // );
          // this.applicationDetails.rewardableAmount =
          //   this.utilityService.getBanglaDigitFromEnglish(
          //     this.applicationDetails.rewardableAmount.toString()
          //   );
        }

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

  calculatePercentage() {
    for (let i = 0; i < this.applicationDetails.rewardAmounts.length; i++) {
      this.tmp =
        this.applicationDetails.rewardAmounts[i].appliedRewardAmount /
        parseFloat(this.applicationDetails.extraReceivedAmount);
      this.rewardPercentage.push(this.tmp * 100);
    }
  }

  setFormValue() {
    this.applicationDetailsStepTwoGroup.patchValue({
      suggestionStatus: this.temp.suggestionStatus,
      suggestionFromNbr: this.temp.suggestionFromNbr,
    });
  }

  setRewardAdmounts() {
    for (let i = 0; i < this.applicationDetails.rewardAmounts.length; i++) {
      this.applicationDetails.rewardAmounts[i].appliedRewardAmount =
        (parseFloat(this.rewardPercentage[i]) *
          parseFloat(
            this.applicationDetailsStepTwoGroup.value.extraReceivedAmount
          )) /
        100;
    }
  }

  forwardToApplicationDetailsStepTwoAccepted() {
    this.router.navigate(
      [
        `../../nbr-admin-step-two-accepted/${this.activateRoute.snapshot.paramMap.get(
          "oid"
        )}`,
      ],
      { relativeTo: this.activateRoute }
    );
  }
}
