import {Component, OnInit} from '@angular/core';
import {DropdownModule} from 'primeng/dropdown';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ApplicationEntity} from '@app/auth/nbr-admin/model/applicationEntity';
import {NbrApplicationService} from '@app/auth/nbr-admin/services/nbr-application.service';
import {ActivatedRoute, Router} from '@angular/router';
import {MessageService} from 'primeng/api';
import { UtilityService } from '@app/common/services/util/utility.service';
import { saveAs }  from 'file-saver';
import { SharedService } from '@app/common/services/shared.service';
import { event } from 'jquery';

@Component({
  selector: "app-application-details-step-two-accepted",
  templateUrl: "./application-details-step-two-accepted.component.html",
  styleUrls: ["./application-details-step-two-accepted.component.css"],
})
export class ApplicationDetailsStepTwoAcceptedComponent implements OnInit {
  public toggleStatus: boolean = true;  
  public isDifferent: boolean = false;
  tempList: DropdownModule;
  temp1List: DropdownModule;
  public isLoading: boolean = false;
  applicationDetailsStepTwoAcceptedGroup: FormGroup;
  applicationDetails: ApplicationEntity;
  applicationDetails2: ApplicationEntity;
  nbrApplicationDetails: ApplicationEntity;
  applicationNbrAdminStepThreeDto: ApplicationEntity;
  public rewardPercentage: any = [];
  public tmp: any = [];
  public rewardAmounts: any = [];
  public tempRewardAmounts: any = [];
  public tempRewardAmountsFalse: any = [];
  public rewardedTotal: any = 0;
  public appliedRewardableAmountInBangla: any;
  public totalRewardedAmountInBangla: any;
  public rewardedAmountInBangla: any;
  public changedRewardValue: any;
  officeName: [""];
  suggestionFromNbr: [""];
  suggestionStatus: [""];
  extraReceivedAmount: [""];
  // public checked: boolean = false;
  // extraReceivedPercentage: [''];
  totalRewardedAmount: [""];
  rewardableAmount: [""];
  checkBoxClickCount: number = 1;
  totalRewardedAmountFromDb: any;
  public rmsRole = "";
  pdfData:any;
  public appliedRewardAmount: number = 0;

  constructor(
    private applicationDetailsStepTwoAccepted: FormBuilder,
    private nbrApplicationService: NbrApplicationService,
    private router: Router,
    private activateRoute: ActivatedRoute,
    private messageService: MessageService,
    private utilityService: UtilityService,
    private sharedService: SharedService 
  ) {
    this.tempList = [
      { bnname: "আবেদন বিবেচনাযোগ্য" },
      { bnname: "আবেদন বিবেচনাযোগ্য নয়" },
      { bnname: "বিবেচনার জন্য প্রয়োজনীয় নথি প্রয়োজন" },
    ];
    this.temp1List = [
      { oid: "টাকার পরিমাণ (অংকে)", name: "টাকার পরিমাণ (অংকে)" },
      { oid: "টাকার পরিমাণ (শতাংশ)", name: "টাকার পরিমাণ (শতাংশ)" },
    ];
  }

  get f() {
    return this.applicationDetailsStepTwoAcceptedGroup.controls;
  }

  ngOnInit(): void {
    this.rmsRole = this.sharedService.getRmsRole();
    if (this.rmsRole !== "role-nbr-admin") {
      this.router.navigate(["page-not-found"]);
    } else {
      this.applicationDetailsStepTwoAcceptedGroup =
        this.applicationDetailsStepTwoAccepted.group({
          officeName: [""],
          suggestionFromNbr: [""],
          suggestionStatus: [""],
          extraReceivedAmount: [""],
          takaUnit: ["টাকার পরিমাণ (অংকে)"],
          extraReceivedPercentage: [""],
          totalRewardedAmount: [""],
          changedRewardableAmount: [""],
        });
      this.getApplicationDetails(
        this.activateRoute.snapshot.paramMap.get("oid")
      );
    }
    
  }

  nextToApplicationDetailsStepThree() {
    // if (this.applicationDetailsStepTwoAcceptedGroup.valid) {
    const requestBody = {
      rewardAmounts: this.applicationDetails.rewardAmounts,
      totalRewardedAmount: this.applicationDetails.totalRewardedAmount,
    };
    this.nbrApplicationService
      .updateApplicationCustomsStepThreeUsingPUT(
        requestBody,
        this.activateRoute.snapshot.params["oid"]
      )
      .subscribe(
        (res) => {
          this.isLoading = true;
          if (res.status === 200) {
            this.messageService.add({
              severity: "success",
              summary: "ধাপ-৩ সফল ভাবে সম্পন্ন হয়েছে",
              detail: "",
            });
            setTimeout(() => {
              this.router.navigate(
                [
                  "../../nbr-admin-step-three",
                  this.activateRoute.snapshot.params["oid"],
                ],
                { relativeTo: this.activateRoute }
              );
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
          this.isLoading = true;
        }
      );
    // } else {
    //     this.isLoading = false;
    //     this.messageService.add({severity: 'error', summary: 'অনুগ্রহ পূর্বক প্রয়োজনীয় তথ্য প্রদান করুন', detail: ''});
    // }
  }

  

  printPreviewApplicationDetailsStepTwoAccepted(){
    const requestBody = {
      rewardAmounts: this.applicationDetails.rewardAmounts,
      totalRewardedAmount: this.applicationDetails.totalRewardedAmount,
    };

    this.nbrApplicationService
      .updateApplicationCustomsStepThreeUsingPUT(
        requestBody,
        this.activateRoute.snapshot.params["oid"]
      )
      .subscribe(
        (res) => {
          this.isLoading = true;
          if (res.status === 200) {
            // this.messageService.add({
            //   severity: "success",
            //   summary: "ধাপ-৩ সফল ভাবে ডাউনলোড হয়েছে",
            //   detail: "",
            // });
            this.nbrApplicationService
              .applicationStepThreeView(
                this.activateRoute.snapshot.paramMap.get("oid")
              )
              .subscribe(
                (res) => {
                  if (res.status === 200) {
                    this.pdfData = res.body;
                    this.printPdf();
                    //saveAs(res.body);
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
            setTimeout(() => {}, 3000);
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
     
  }
// Modified By Arif
// NEW
// this method is made to make a pdf printable view.
  printPdf() {
    const pdfUrl = URL.createObjectURL(this.pdfData);
    const popupWin = window.open(pdfUrl, '_blank', 'fullscreen=yes');
    // setTimeout(() => {
    //   popupWin?.print();
    // }, 1000);
  }


  prevToApplicationDetailsStepTwo() {
    this.router.navigate(
      ["../../nbr-admin-step-two", this.activateRoute.snapshot.params["oid"]],
      { relativeTo: this.activateRoute }
    );
  }

  getApplicationDetails(oid: string) {
    
    this.isLoading = true;    
    this.nbrApplicationService.getApplicationDetails(oid).subscribe(      
      (res) => {
        this.isLoading = true;
        if (res.status === 200) {
          /*
                    From tempRewardAmounts we find if isRewarded == 'true', then we add that item to rewardAmounts list,
                    becasue we are assuming this page will only show them who has inRewarded == 'true'.

                    After that we set this.rewardAmounts list to this.applicationDetails.rewardAmounts.
                    */
                
          this.tempRewardAmounts = res.body.applicationCustomsStepOneDto.rewardAmounts;
            console.error(res.body.applicationCustomsStepOneDto.rewardAmounts);
            
          this.totalRewardedAmountFromDb =
            res.body.applicationNbrAdminStepThreeDto.totalRewardedAmount;
            
          for (var i = 0; i < this.tempRewardAmounts.length; i++) {                    
            this.appliedRewardAmount += this.tempRewardAmounts[i].appliedRewardAmount;
            if (
              ["False", "false"].includes(
                this.tempRewardAmounts[i].isRewarded.toString()
              ) ||
              this.tempRewardAmounts[i].isRewarded == false ||
              this.tempRewardAmounts[i].isRewarded.toString() == "False" ||
              this.tempRewardAmounts[i].isRewarded.toString() == "false"
            ) {
              this.tempRewardAmountsFalse.push(this.tempRewardAmounts[i]);
            } else {
              this.rewardAmounts.push(this.tempRewardAmounts[i]);
            }
          }
                   
          this.applicationDetails = res.body.applicationCustomsStepOneDto;
          this.applicationDetails2 = res.body.applicationNbrAdminStepTwoDto;
          this.applicationDetails.rewardAmounts = this.rewardAmounts;   
          console.log(this.applicationDetails);
                           

          for (
            let i = 0;
            i < this.applicationDetails.rewardAmounts.length;
            i++
          ) {
             

            /*
                        if isRewarded == true setting rewardAmount.isRewarded to true otherwise false
                        */
            if (
              ["True", "true"].includes(
                this.applicationDetails.rewardAmounts[i].isRewarded.toString()
              ) ||
              this.applicationDetails.rewardAmounts[i].isRewarded == true ||
              this.applicationDetails.rewardAmounts[i].isRewarded.toString() ==
                "True" ||
              this.applicationDetails.rewardAmounts[i].isRewarded.toString() ==
                "true"
            ) {
              this.applicationDetails.rewardAmounts[i].isRewarded = true;
            } else if (
              ["False", "false"].includes(
                this.applicationDetails.rewardAmounts[i].isRewarded.toString()
              ) ||
              this.applicationDetails.rewardAmounts[i].isRewarded == false ||
              this.applicationDetails.rewardAmounts[i].isRewarded.toString() ==
                "False" ||
              this.applicationDetails.rewardAmounts[i].isRewarded.toString() ==
                "false"
            ) {
              // this.applicationDetails.rewardAmounts[i].isRewarded = false;
            }
          }
          var count = 0;
          console.log(this.tempRewardAmounts);
          
          for (var j = 0; j < this.tempRewardAmounts.length; j++) {
            console.log(this.tempRewardAmounts[j].rewardedAmount);
            console.log(this.tempRewardAmounts[j].appliedRewardAmount);                        
            if (
              this.tempRewardAmounts[j].rewardedAmount == this.tempRewardAmounts[j].appliedRewardAmount
            ) {
              this.isDifferent = true;
              count = count + 1;
            }
          }
            console.log(this.isDifferent);
            console.log(count);
            
            
          /*
                    this count is for checking if rewardedAmount and appliedRewardedAmount value same for each case.
                    So that we can trigger if we need to calculate of not.

                    If values are different we can show them from data retrieved from database but of values are not different
                    then, we show same data for both rewardedAmount and appliedRewardedAmount.
                    */
          this.nbrApplicationDetails = res.body.applicationNbrAdminStepTwoDto;
          if(this.isDifferent == true && count == this.tempRewardAmounts.length){
              this.setFormValue();                           
              this.calculatePercentage();
          if(this.nbrApplicationDetails.applicationStatus==='Submitted'){
              this.setRewardAmounts();
              this.calculateRewardedTotal();
          }
          }
          for (var i = 0; i < this.rewardAmounts.length; i++) {
            if (
              this.rewardAmounts[i].rewardedAmount == null ||
              this.rewardAmounts[i].rewardedAmount == ""
            ) {
              console.log("Running null value side");              
              this.setFormValue();
              this.calculatePercentage();

              if (
                this.nbrApplicationDetails.applicationStatus === "Submitted"
              ) {
                this.setRewardAmounts();
                this.calculateRewardedTotal();
              }
            }
          }
          if (
            this.totalRewardedAmountFromDb != null ||
            this.totalRewardedAmountFromDb != ""
          ) {
            this.applicationDetailsStepTwoAcceptedGroup.patchValue({
              changedRewardableAmount: this.totalRewardedAmountFromDb,
            });
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


          this.calculateRewardedTotal();

          // this.utilityService.convertEnglishToBangla(this.applicationDetails.rewardAmounts);
          // this.totalRewardedAmountInBangla = this.utilityService.getBanglaDigitFromEnglish(this.applicationDetails.totalRewardedAmount.toString());
          // this.appliedRewardableAmountInBangla = this.utilityService.getBanglaDigitFromEnglish(this.applicationDetails.rewardableAmount.toString());
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
        console.log("Log from subcribe end");
        
        console.log(this.applicationDetails);
        console.log(this.applicationDetails.rewardableAmount);
        
      }
    );

  }

  setFormValue() {
    this.applicationDetailsStepTwoAcceptedGroup.patchValue({
      changedRewardableAmount: this.applicationDetails.rewardableAmount,
    });
  }

  calculatePercentage() {
    this.rewardPercentage = [];
    for (let i = 0; i < this.applicationDetails.rewardAmounts.length; i++) {
      this.tmp =
        this.applicationDetails.rewardAmounts[i].appliedRewardAmount /
        parseFloat(this.applicationDetails.rewardableAmount);
      this.rewardPercentage.push(this.tmp * 100);
      console.log(this.applicationDetails.rewardAmounts[i].appliedRewardAmount);
    }
    console.log("Calculate percentage");
    
    
    
  }
  
  setRewardAmounts() {                  
    this.calculatePercentage();
    var changedRewardableAmount = this.getEnglishDigitFromBangla(this.applicationDetailsStepTwoAcceptedGroup.get("changedRewardableAmount").value.toString());      
    var rewardableAmountset = parseInt(changedRewardableAmount);
     if(isNaN(rewardableAmountset)){
      rewardableAmountset = 0;
     }            
    if (
      rewardableAmountset > +this.applicationDetails.rewardableAmount
    ) {           
      this.messageService.add({
        severity: "error",
        summary: "সুপারিশকৃত টাকার পরিমাণ আবেদনকৃত অর্থের পরিমাণ থেকে বেশি",
        detail: "",
      });            
    }   
    for (let i = 0; i < this.applicationDetails.rewardAmounts.length; i++) {
      let rewardAmount =
        (this.rewardPercentage[i]*
          rewardableAmountset) /100;
      this.applicationDetails.rewardAmounts[i].rewardedAmount = parseFloat(
        rewardAmount.toFixed(2)
      );            
    }
    this.calculateRewardedTotal();
  }

  setRewardAmountsByPercentage() {

  //   var extraReceivedPercentage = this.getEnglishDigitFromBangla(this.applicationDetailsStepTwoAcceptedGroup.get("extraReceivedPercentage").value);
  //   console.log(this.applicationDetailsStepTwoAcceptedGroup.get("changedRewardableAmount").value);
  //  var extraReceivedPercentageset = parseInt(extraReceivedPercentage);
  //  if(isNaN(extraReceivedPercentageset)){
  //   extraReceivedPercentageset = 0;
  //  }
  //   console.log("This is changed reward amount");      
  //   console.log(extraReceivedPercentageset);

    for (let i = 0; i < this.applicationDetails.rewardAmounts.length; i++) {
      let rewardAmount =
        (+this.applicationDetails.rewardAmounts[i].appliedRewardAmount / 100) *
        +this.applicationDetailsStepTwoAcceptedGroup.value
          .extraReceivedPercentage;
      this.applicationDetails.rewardAmounts[i].rewardedAmount =
        +rewardAmount.toFixed(2);
    }
    this.calculateRewardedTotal();
  }
  calculateRewardedTotal() {
    let total = 0;
    for (let i = 0; i < this.applicationDetails.rewardAmounts.length; i++) {
      if (this.applicationDetails.rewardAmounts[i].isRewarded) {
        total += this.applicationDetails.rewardAmounts[i].rewardedAmount; 
      }
      this.applicationDetails.totalRewardedAmount = total;
      console.log("This is total");
      console.log(total);
      
            
      if (total > +this.applicationDetails.rewardableAmount) {
        this.messageService.add({
          severity: "error",
          summary: "সুপারিশকৃত টাকার পরিমাণ আবেদনকৃত অর্থের পরিমাণ থেকে বেশি",
          detail: "",          
        });       
      }
    }

    console.log("This is total rewarded amount");
    console.log(this.applicationDetails.totalRewardedAmount);
    
    
   
    // this.totalRewardedAmountInBangla = this.utilityService.getBanglaDigitFromEnglish(this.applicationDetails.totalRewardedAmount.toString());
  }

  handleCheckbox($event: any, index) {
    console.log($event);
    if (this.applicationDetails.rewardAmounts[index].isRewarded == true) {
      this.applicationDetails.rewardAmounts[index].isRewarded = false;
      this.applicationDetails.rewardAmounts[index].rewardedAmount = 0;
    } else {
      if(this.applicationDetailsStepTwoAcceptedGroup.get("changedRewardableAmount").value != null){
        var changedRewardableAmount = this.getEnglishDigitFromBangla(this.applicationDetailsStepTwoAcceptedGroup.get("changedRewardableAmount").value.toString());      
      }
      if(this.applicationDetailsStepTwoAcceptedGroup.get("changedRewardableAmount").value == null){
        var changedRewardableAmount = this.appliedRewardAmount.toString();
      }
      var rewardableAmountset = parseInt(changedRewardableAmount);
      if(isNaN(rewardableAmountset)){
      rewardableAmountset = 0;
     } 
      this.applicationDetails.rewardAmounts[index].rewardedAmount = (((rewardableAmountset*100)/(this.appliedRewardAmount))/100)*(this.applicationDetails.rewardAmounts[index].appliedRewardAmount);
      console.log("this is applied change");
      console.log(this.applicationDetails.rewardAmounts[index].rewardedAmount);
      
      
      this.applicationDetails.rewardAmounts[index].isRewarded = true;
      
    }  
       
    this.calculateRewardedTotal();    
      
  }

  handleCheckbox2(even: Event, index: number) {
    console.log(even);
   for (let k = 0; k < this.tempRewardAmountsFalse.length; k++) {
    var checked = document.getElementById("falseCheck"+k) as HTMLInputElement;
    if(checked.checked){
      if(this.applicationDetailsStepTwoAcceptedGroup.get("changedRewardableAmount").value != null){
        var changedRewardableAmount = this.getEnglishDigitFromBangla(this.applicationDetailsStepTwoAcceptedGroup.get("changedRewardableAmount").value.toString());      
      }
      if(this.applicationDetailsStepTwoAcceptedGroup.get("changedRewardableAmount").value == null){
        var changedRewardableAmount = this.appliedRewardAmount.toString();
      }
      var rewardableAmountset = parseInt(changedRewardableAmount);
      if(isNaN(rewardableAmountset)){
      rewardableAmountset = 0;
     } 
      this.tempRewardAmountsFalse[index].rewardedAmount = (((rewardableAmountset*100)/(this.appliedRewardAmount))/100)*(this.tempRewardAmountsFalse[index].appliedRewardAmount);
      console.log("this is applied change");
      console.log(this.applicationDetails.rewardAmounts[index].rewardedAmount);

      this.tempRewardAmountsFalse[index].isRewarded = true;
      this.applicationDetails.rewardAmounts.push(this.tempRewardAmountsFalse[index]);
      this.tempRewardAmountsFalse.splice(index,1);
      
    }    
   }
    this.calculateRewardedTotal();
  }

  onChecked($event, index) {
    if (this.checkBoxClickCount == 1) {
      for (let i = 0; i < this.applicationDetails.rewardAmounts.length; i++) {
        if (i === index) {
          this.applicationDetails.rewardAmounts[i].isRewarded = $event.checked;
        } else {
          this.applicationDetails.rewardAmounts[i].isRewarded = false;
        }
      }
      this.checkBoxClickCount += 1;
    } else {
      this.applicationDetails.rewardAmounts[index].isRewarded = $event.checked;
      this.checkBoxClickCount += 1;
    }
    this.calculateRewardedTotal();
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
      for (let x in finalEnlishToBanglaNumber) {
        value.rewardedAmount = value.rewardedAmount
          .toString()
          .replace(new RegExp(x, "g"), finalEnlishToBanglaNumber[x]);
      }
    });
  }

  setUpdatedRewardAmount($event, i) {
   var tempModifiedAmount = parseFloat(this.getEnglishDigitFromBangla($event.target.value));
   console.log(tempModifiedAmount);
    this.applicationDetails.rewardAmounts[i].rewardedAmount =
      +tempModifiedAmount;
    this.calculateRewardedTotal();
    this.changedRewardValue = "";
  }

  setUpdatedRewardAmount2($event, i) {
    this.tempRewardAmountsFalse[i].rewardedAmount =
      +parseFloat(this.getEnglishDigitFromBangla($event.target.value));
    this.calculateRewardedTotal();
    this.changedRewardValue = "";
  }

  calculateRewardedTotalWithValueChange() {}

  selectUnit($event) {
    console.log($event.value);
  }

  forwardToApplicationDetailsStepThree() {
    this.router.navigate(
      [
        `../../nbr-admin-step-three/${this.activateRoute.snapshot.paramMap.get(
          "oid"
        )}`,
      ],
      { relativeTo: this.activateRoute }
    );
  }

  //   document.getElementsByClassName('edit-items')[0].oninput = function () {
  //     var max = parseInt(this.max);

  //     if (parseInt(this.value) > max) {
  //         this.value = max;
  //     }
  // }

  getEnglishDigitFromBangla(inputString: string) {
    let finalEnlishToBanglaNumber = {
      "০": "0",
      "১": "1",
      "২": "2",
      "৩": "3",
      "৪": "4",
      "৫": "5",
      "৬": "6",
      "৭": "7",
      "৮": "8",
      "৯": "9",
    };
    for (let x in finalEnlishToBanglaNumber) {
      inputString = inputString.replace(
        new RegExp(x, "g"),
        finalEnlishToBanglaNumber[x]
      );
    }
    return inputString;
  }
}
