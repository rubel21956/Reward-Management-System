import { ChangeDetectorRef, Component, OnInit, } from "@angular/core";
import { AbstractControl, ValidatorFn, FormBuilder, FormGroup, NgForm, Validators } from "@angular/forms";
import { DomSanitizer } from "@angular/platform-browser";
import { ActivatedRoute, Route, Router } from "@angular/router";
import { OfficeService } from "@app/auth/office/services/office.service";
import { SharedService } from "@app/common/services/shared.service";
import { MenuItem, MessageService } from "primeng/api";
import { DropdownModule } from "primeng/dropdown";
import { ApplicationService } from "../services/application.service";
import { NgModel } from "@angular/forms";
import { collapseTextChangeRangesAcrossMultipleVersions } from "typescript";
import { resourceServerUrl } from "@app/common/constants/server-settings";
import { FileService } from "@app/common/services/file.service";
import { HttpEventType, HttpResponse } from "@angular/common/http";
import { ApplicationCustomsStepOne } from "../model/applicationCustomsStepOne";
import { PrimeNGConfig } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { formatDate } from "@angular/common";
import Handsontable from "handsontable";
import { NbrApplicationService } from "@app/auth/nbr-admin/services/nbr-application.service";
import { ApplicationAttachment } from "../model/applicationAttachment";



@Component({
  selector: "app-application-customs-step-one",
  templateUrl: "./application-customs-step-one.component.html",
  styleUrls: ["./application-customs-step-one.component.css"],
})
export class ApplicationCustomsStepOneComponent {

  // this toggleStatus variable stores boolean value depending on which checkbox remain checked and unchecked!
  public toggleStatus: boolean = false;
  public isLoading: boolean = false;
  public draftSaved: boolean = false;
  public uploadedFilesName: string = '';
  billOfEntryNoCheck: any;
  public oid: string;
  public displayModal = false;
  public data: any;

  minDate: Date;
  maxDate: Date;
  sendGroup: FormGroup;
  applicationCustomsStepOneGroup: FormGroup;
  temp: ApplicationCustomsStepOne;
  temp1: ApplicationCustomsStepOne;
  investigatorOffice: DropdownModule;
  investigatorOffice2: DropdownModule;
  applyingOffice: any = [];
  prize: DropdownModule;
  rewardCategory: any;
  rewardAmounts: any = [];
  officerAmounts: any = [];
  categories: any;
  extraTax: any;
  public sessionList: any[];
  rewardAmountsGroup: FormGroup;
  informerGroup: FormGroup;
  officeWelfareGroup: FormGroup;
  nbrWelfareGroup: FormGroup;
  officerGroup: FormGroup;
  isTableAvailable: boolean = false;
  attachment: any = [];
  rewardableAmount: any;
  employeeName = "";
  designation = "";
  advancePaidAmount = "";
  appliedRewardAmount = "";
  isRewarded: string = "false";
  submitted: boolean = false;

  public totalOfficerAmount: any = 0;
  public totalAmount: any = 0;
  public rmsRole = "";
  public tableSettings: any;
  public tableSettings2: any;
  public dataset: any[];
  public dataset2: any[];
  public officersList: any = [];
  public officersMap = new Map();
  public showRewardData: any = [];
  public stepOneBOEFileProgress: any;
  public tempDataset: any = [];
  public currentOfficeId: string = '';
  public distributedAmount = 0;
  public toStopDraftAfterNextButton = false;

  public pageStatus: any;
  public deletableFileName: string = "";
  private rowCountArray: any = [];
  items: MenuItem[];
  activeIndex: number = 0;

  public realFileName = "";
  public tempAmount: number = 0;
  en: { firstDayOfWeek: number; dayNames: string[]; dayNamesShort: string[]; dayNamesMin: string[]; monthNames: string[]; monthNamesShort: string[]; today: string; clear: string; dateFormat: string; weekHeader: string; };
  // Modified By Arif
  // This variable is for check the billofentry no and as weel as Date, office
  billOfEntryStatusChech: boolean = false;

  constructor(
    private nbrApplicationService: NbrApplicationService,
    private applicationCustomsStepOne: FormBuilder,
    private applicationService: ApplicationService,
    private messageService: MessageService,
    private router: Router,
    private officeService: OfficeService,
    private rewardAmount: FormBuilder,
    private nbrWelfareAmounts: FormBuilder,
    private informerAmounts: FormBuilder,
    private fileService: FileService,
    private activateRoute: ActivatedRoute,
    private sharedService: SharedService,
    private config: PrimeNGConfig,
    private translateService: TranslateService
  ) {

    const today = new Date();
    this.minDate = new Date("January 01, 1900");
    this.maxDate = new Date(today);

    this.prize = [
      { number: "ক্যাটাগরি-১" },
      { number: "ক্যাটাগরি-২" },
      { number: "ক্যাটাগরি-৩" },
    ];

    this.rewardCategory = [
      {
        oid: "",
        category: "তথ্য সরবরাহকারী",
        percentage: "২০%",
        amount: "০",
        is_rewarded: "True",
      },
      {
        oid: "",
        category: "আটককারী কর্মকর্তা / কর্মচারী ",
        percentage: "৮৫%",
        amount: "০",
        is_rewarded: "True",
      },
      {
        oid: "",
        category: "কাস্টমস হাউস, এর কল্যাণ তহবিল",
        percentage: "১২%",
        amount: "০",
        is_rewarded: "True",
      },
      {
        oid: "",
        category: "জারাবো  এর কল্যাণ তহবিল",
        percentage: "৩%",
        amount: "০",
        is_rewarded: "True",
      },
    ];
  }

  get f() {
    return this.applicationCustomsStepOneGroup.controls;
  }

  ngOnInit(): void {
    // Step Module Implementation        
    this.distributedAmount = 0;
    this.items = [{
      label: '১ম ধাপ',
      command: (event: any) => {
        this.activeIndex = 0;
        this.messageService.add({ severity: 'success', summary: 'প্রথম ধাপ', detail: event.item.label });
        if (this.activateRoute.snapshot.paramMap.get("oid") !== null) {
          setTimeout(() => {
            this.router.navigate(['application-customs-step-one',
              this.activateRoute.snapshot.paramMap.get("oid")])
          }, 2000);
        } else {
          setTimeout(() => {
            this.router.navigate(['application-customs-step-one'])
          }, 2000);
        }
      }
    },
    {
      label: '২য় ধাপ',
      command: (event: any) => {
        this.activeIndex = 1;
        this.messageService.add({ severity: 'success', summary: 'দ্বিতীয় ধাপ', detail: event.item.label });
        setTimeout(() => {
          this.router.navigate(['application-customs-step-two',
            this.activateRoute.snapshot.paramMap.get("oid")])
        }, 2000);
      }
    },
    {
      label: '৩য় ধাপ',
      command: (event: any) => {
        this.activeIndex = 2;
        this.messageService.add({ severity: 'success', summary: 'তৃতীয় ধাপ', detail: event.item.label });
        setTimeout(() => {
          this.router.navigate(['application-customs-step-four',
            this.activateRoute.snapshot.paramMap.get("oid")])
        }, 2000);
      }
    },
    {
      label: 'চূড়ান্ত ধাপ',
      command: (event: any) => {
        this.activeIndex = 3;
        this.messageService.add({ severity: 'success', summary: 'চতুর্থ ধাপ', detail: event.item.label });
        setTimeout(() => {
          this.router.navigate(['application-customs-step-five',
            this.activateRoute.snapshot.paramMap.get("oid")])
        }, 2000);
      }
    }
    ];

    if (this.activateRoute.snapshot.paramMap.get("oid") === null) {
      this.pageStatus = 1;
    } else {
      this.pageStatus = 2;
    }

    //For HandonTable  
    this.tableSettings = {
      viewportColumnRenderingOffset: 27,
      viewportRowRenderingOffset: "auto",
      colWidths: 290,
      // language:'bn-BD',
      height: 'auto',
      licenseKey: 'non-commercial-and-evaluation',
      width: 924,
      maxRows: 100,
      rowHeaders: true,
      i18n: 'i18n',
      manualRowResize: true,
      manualColumnResize: false,
      observeChanges: true,
      columns: [
        {
          data: 'employeeName',
        },
        {
          data: 'designation'
        },
        {
          data: 'appliedRewardAmount',
        }
      ],

      colHeaders: ["নাম (বাংলায়)", 'পদবী (বাংলায়)', "আবেদনকৃত অর্থের পরিমাণ (বাংলায়)"],
      manualRowMove: true,
      manualColumnMove: true,
      contextMenu: true,
      filters: true,
      dropdownMenu: false,
      afterRemoveRow: (change) => {
        this.distributedAmount = 0;
        this.dataset.forEach((a) => {
          const amount = parseFloat(a.appliedRewardAmount != '' ? this.getEnglishDigitFromBangla(a.appliedRewardAmount) : '0');
          this.distributedAmount += amount
        });
        if (this.dataset.length == 0) {
          this.addRowtoDataSet();
        }
      },
      afterChange: (change) => {

        if (change != null) {
          for (let row = 0; row < change.length; row++) {
            if (change[row][1] == 'employeeName' && change[row][3] == null) {
              this.dataset[change[row][0]].employeeName = '';
            }
            if (change[row][1] == 'designation' && change[row][3] == null) {
              this.dataset[change[row][0]].designation = '';
            }
            if (change[row][1] == 'appliedRewardAmount' && change[row][3] == null) {
              this.dataset[change[row][0]].appliedRewardAmount = '0';
            }

          }


        }

        let charecter: any
        if (change != null) {
          charecter = change[0][3] == null ? '0' : change[0][3];
          if (change[0][1] == 'appliedRewardAmount' && charecter.match('^[0-9১২৩৪৫৬৭৮৯০.]*$') != null) {
            this.distributedAmount = 0;

            this.dataset[change[0][0]].appliedRewardAmount = this.getBanglaDigitFromEnglish(charecter);
            this.dataset.forEach((a) => {
              var amount = parseFloat(a.appliedRewardAmount != '' || a.appliedRewardAmount != null ? this.getEnglishDigitFromBangla(a.appliedRewardAmount) : '0');
              if (isNaN(amount)) {
                amount = 0;
              }
              this.distributedAmount += amount
            });
          } else if (change[0][1] == 'appliedRewardAmount' && charecter.match('^[0-9১২৩৪৫৬৭৮৯০.]*$') == null) {
            this.dataset[change[0][0]].appliedRewardAmount = '0'
            this.messageService.add({
              severity: "error",
              summary: "শুধু সংখ্যা প্রদান করুন।",
              detail: "আবেদনকৃত অর্থের পরিমাণ " + this.getBanglaDigitFromEnglish((change[0][0] + 1).toString()) + " নং লাইনে।",
            });
          }
        }
        this.distributedAmount = 0;
        this.dataset.forEach((a) => {
          if (a.appliedRewardAmount.match('^[0-9১২৩৪৫৬৭৮৯০.]*$')) {
            var amount = parseFloat(a.appliedRewardAmount != '' || a.appliedRewardAmount != null ? this.getEnglishDigitFromBangla(a.appliedRewardAmount) : '0');
            if (isNaN(amount)) {
              amount = 0;
            }

            this.distributedAmount += amount
          }

        });


      },
      afterPaste: (paste) => {
        this.distributedAmount = 0;
        this.dataset.forEach((a) => {
          if (a.appliedRewardAmount.match('^[0-9১২৩৪৫৬৭৮৯০.]*$')) {
            var amount = parseFloat(a.appliedRewardAmount != '' || a.appliedRewardAmount != null ? this.getEnglishDigitFromBangla(a.appliedRewardAmount) : '0');
            if (isNaN(amount)) {
              amount = 0;
            }

            this.distributedAmount += amount
          }

        });
      },

      afterValidate: function (isValid: any, value: boolean, row: any, prop: any) {
        if (value == false) {
          alert("Invalid")
          value = isValid
          row = 'inserted invalid value'
          prop = 'row index changed'
        }
      },
    };
    this.dataset2 = [
      {
        employeeName: "তথ্য সরবরাহকারী",
        appliedRewardAmount: "",
        designation: "তথ্য সরবরাহকারী",
        advancePaidAmount: "",
        isRewarded: "True",
      },
      {
        employeeName: "জারাবো এর কল্যাণ তহবিল",
        appliedRewardAmount: "",
        designation: "জারাবো এর কল্যাণ তহবিল",
        advancePaidAmount: "",
        isRewarded: "True",
      },
      {
        employeeName: "কাস্টমস হাউস এর কল্যাণ তহবিল",
        appliedRewardAmount: "",
        designation: "কাস্টমস হাউস এর কল্যাণ তহবিল",
        advancePaidAmount: "",
        isRewarded: "True",
      },
    ];

    if (this.activateRoute.snapshot.paramMap.get("oid") !== null) {
      this.dataset = [];
    } else {
      this.dataset = [
        {
          employeeName: "",
          appliedRewardAmount: "",
          designation: "",
          advancePaidAmount: "",
          isRewarded: "True",
          sl: ""
        },
        {
          employeeName: "",
          appliedRewardAmount: "",
          designation: "",
          advancePaidAmount: "",
          isRewarded: "True",
          sl: ""
        },

      ];
    }

    this.tableSettings2 = {
      viewportColumnRenderingOffset: 27,
      viewportRowRenderingOffset: "auto",
      colWidths: 290,
      // language:'bn-BD',
      height: 'auto',
      licenseKey: 'non-commercial-and-evaluation',
      width: 924,
      maxRows: 3,
      rowHeaders: ["i", "ii", "iii"],
      i18n: 'i18n',
      manualRowResize: false,
      manualColumnResize: false,
      observeChanges: true,
      columns: [
        {
          data: 'employeeName',
        },
        {
          data: 'designation'
        },
        {
          data: 'appliedRewardAmount',
        }
      ],

      manualRowMove: false,
      manualColumnMove: false,
      contextMenu: false,
      filters: true,
      dropdownMenu: false,
      afterValidate: function (isValid: any, value: boolean, row: any, prop: any) {
        if (value == false) {
          alert("Invalid")
          value = isValid
          row = 'inserted invalid value'
          prop = 'row index changed'
        }
      },
    };

    this.translateService.setDefaultLang('en');
    this.rmsRole = this.sharedService.getRmsRole();

    if ((this.rmsRole !== "role-operator") && (this.rmsRole !== "role-nbr-investigation")) {
      this.router.navigate(["page-not-found"]);
    } else if (this.rmsRole === "role-operator" || this.rmsRole === "role-nbr-investigation") {

      this.applicationCustomsStepOneGroup =
        this.applicationCustomsStepOne.group({
          oid: [""],
          billOfEntryDate: ["", Validators.required],
          anomalyCaptureDate: ["", Validators.required],
          anomalyReason: [""],
          anomalyReportNo: [""],
          appealOrderDate: ["", Validators.required],
          appealOrderNo: ["", Validators.required],
          applyingOfficeOid: ["", Validators.required],
          billOfEntryNo: ["", Validators.required],
          category: [""],
          collectedDutyAndPenaltyAmount: ["", Validators.required],
          customsRewardFileNumber: [""],
          exporterImporterDutyAmount: ["", Validators.required],
          nbrRewardFileNumber: [""],
          officeHeadsName: ["", Validators.required],
          officeOid: ["", Validators.required],
          referenceNo: ["", Validators.required],
          remarksFromCustomsOffice: [""],
          rewardableAmount: [""],
          applicationStatus: [""],
          applicationStatusBn: [""],
          rewardAmounts: this.rewardAmounts,
          applicationAttachments: this.attachment,
          officeName: [""],
          officeAddress: [""],
          extraReceivedAmount: [""],
          accusedCompanyName: [""],
          accusedCompanyAddress: [""],
        });


      if (this.rmsRole === "role-operator" || this.rmsRole === "role-nbr-investigation") {
        this.applicationService.getMyProfile(this.sharedService.getcurrentLoggedInUserID()).subscribe((userProfile) => {
          this.currentOfficeId = userProfile.body.currentOffice;

          this.applicationCustomsStepOneGroup =
            this.applicationCustomsStepOne.group({
              oid: [""],
              billOfEntryDate: ["", Validators.required],
              anomalyCaptureDate: ["", Validators.required],
              anomalyReason: [""],
              anomalyReportNo: [""],
              appealOrderDate: ["", Validators.required],
              appealOrderNo: ["", Validators.required],
              applyingOfficeOid: [this.currentOfficeId, Validators.required],
              billOfEntryNo: ["", Validators.required],
              category: [""],
              collectedDutyAndPenaltyAmount: ["", Validators.required],
              customsRewardFileNumber: [""],
              exporterImporterDutyAmount: ["", Validators.required],
              nbrRewardFileNumber: [""],
              officeHeadsName: ["", Validators.required],
              officeOid: [this.currentOfficeId, Validators.required],
              referenceNo: ["", Validators.required],
              remarksFromCustomsOffice: [""],
              rewardableAmount: [""],
              applicationStatus: [""],
              applicationStatusBn: [""],
              rewardAmounts: this.rewardAmounts,
              applicationAttachments: this.attachment,
              officeName: [""],
              officeAddress: [""],
              extraReceivedAmount: [""],
              accusedCompanyName: [""],
              accusedCompanyAddress: [""],
            });
        })

      }
      this.informerGroup = this.informerAmounts.group({
        employeeName: "তথ্য সরবরাহকারী",
        designation: "তথ্য সরবরাহকারী",
        advancePaidAmount: "0",
        appliedRewardAmount: "0",
        isRewarded: "false",
        sl: ""
      });

      this.nbrWelfareGroup = this.nbrWelfareAmounts.group({
        employeeName: "জারাবো এর কল্যাণ তহবিল",
        designation: "জারাবো এর কল্যাণ তহবিল",
        advancePaidAmount: "0",
        appliedRewardAmount: "",
        isRewarded: "True",
        sl: ""
      });

      this.rewardAmountsGroup = this.rewardAmount.group({
        employeeName: "",
        designation: "",
        advancePaidAmount: "",
        appliedRewardAmount: "",
        isRewarded: "True",
        sl: ""
      });

      this.officerGroup = this.rewardAmount.group({
        employeeName: "",
        designation: "",
        advancePaidAmount: "",
        appliedRewardAmount: "",
        isRewarded: "True",
        sl: ""
      });

      this.officeWelfareGroup = this.rewardAmount.group({
        employeeName: "কাস্টমস হাউস এর কল্যাণ তহবিল",
        designation: "কাস্টমস হাউস এর কল্যাণ তহবিল",
        advancePaidAmount: "0",
        appliedRewardAmount: "",
        isRewarded: "True",
        sl: ""
      });
      this.getApplyingOfficeList();
      this.oid = this.activateRoute.snapshot.paramMap.get("oid");
      if (this.activateRoute.snapshot.paramMap.get("oid") !== null) {
        this.getApplicationCustomsStepOneDetailsByOid();
      }
      this.getInvestigatorOfficeList();
      this.attachment = [];
      this.applicationCustomsStepOneGroup
        .get("applyingOfficeOid")
        .valueChanges.subscribe((value) => {
          this.applicationCustomsStepOneGroup.patchValue({
            officeName: "",
            officeAddress: "",
          });
        });



    }



    this.translate("bn");
    this.toggleStatus = true;
    this.rewardableAmount = 0;


  }

  detectChanges = (hotInstance, changes, source) => {
  };

  translate(lang: string) {
    this.translateService.use(lang);
    this.translateService.get('primeng').subscribe(res => this.config.setTranslation(res));
  }


  calculate() {
    let collectedDutyAndPenaltyAmount: any = 0;
    let exporterImporterDutyAmount: any = 0;
    let temporaryChecker: number = this.applicationCustomsStepOneGroup.get("exporterImporterDutyAmount").value == null ? 0 : this.applicationCustomsStepOneGroup.get("exporterImporterDutyAmount").value.length

    if (temporaryChecker == 0 && this.temp != null) {
      exporterImporterDutyAmount = 0; this.temp.extraReceivedAmount = 0; this.temp.rewardableAmount = 0;
    } else {
      if (this.applicationCustomsStepOneGroup.get("exporterImporterDutyAmount").value.length) {
        exporterImporterDutyAmount = parseFloat(this.getEnglishDigitFromBangla(this.applicationCustomsStepOneGroup.get("exporterImporterDutyAmount").value));
      }

    }

    if (temporaryChecker == 0) {
      exporterImporterDutyAmount = 0; this.extraTax = 0; this.rewardableAmount = 0;
    }

    if (this.applicationCustomsStepOneGroup.get("collectedDutyAndPenaltyAmount").value == '') {
      collectedDutyAndPenaltyAmount = 0; this.extraTax = ''; this.temp == undefined ? 0 : this.temp.extraReceivedAmount = 0;
      this.temp == undefined ? '' : this.temp.rewardableAmount = '';
    } else {
      collectedDutyAndPenaltyAmount = parseFloat(this.applicationCustomsStepOneGroup.get("collectedDutyAndPenaltyAmount").value == null ? '0' : this.getEnglishDigitFromBangla(this.applicationCustomsStepOneGroup.get("collectedDutyAndPenaltyAmount").value));
    }


    if (
      collectedDutyAndPenaltyAmount > 0 && exporterImporterDutyAmount >= 1
    ) {
      this.extraTax = (collectedDutyAndPenaltyAmount - exporterImporterDutyAmount);
      this.extraTax = (this.extraTax > 0 && collectedDutyAndPenaltyAmount > 0) ? this.extraTax : 'আদায়কৃত জরিমানা ও শুল্ক-করের পরিমাণ সঠিক নয়।';
      this.rewardableAmount = parseFloat((this.extraTax * 0.1).toFixed(2)) < 0 ? 0 : (this.extraTax * 0.1).toFixed(2);
      this.applicationCustomsStepOneGroup.value.rewardableAmount = (
        this.extraTax * 0.1
      ).toFixed(2);
      this.applicationCustomsStepOneGroup.value.extraReceivedAmount = (
        this.extraTax
      );
      this.temp == undefined ? 0 : this.temp.rewardableAmount = this.extraTax * 0.1;
      if (this.temp != null) {
        this.temp.extraReceivedAmount = this.extraTax > 0 ? this.extraTax : 'আদায়কৃত জরিমানা ও শুল্ক-করের পরিমাণ সঠিক নয়।';
        this.temp.rewardableAmount = this.rewardableAmount >= 0 ? this.rewardableAmount : '';
      }
      if (temporaryChecker == 0) {
        this.temp.rewardableAmount = 0;

      }

    }
    if (
      parseFloat(this.applicationCustomsStepOneGroup.value.rewardableAmount) > 0
    ) {
      const amount =
        +this.applicationCustomsStepOneGroup.value.rewardableAmount;
      // this.rewardCategory[0].amount = (amount * 0.2).toFixed(2);
      if (this.informerGroup.value.isRewarded === "True") {
        this.rewardCategory[0].amount = (amount * 0.85).toFixed(2);
        this.rewardCategory[1].amount = (amount * 0.85).toFixed(2);
        this.rewardCategory[2].amount = (amount * 0.12).toFixed(2);
        this.rewardCategory[3].amount = (amount * 0.03).toFixed(2);
      } else {
        this.rewardCategory[0].amount = 0;
        this.rewardCategory[1].amount = (amount * 0.85).toFixed(2);
        this.rewardCategory[2].amount = (amount * 0.12).toFixed(2);
        this.rewardCategory[3].amount = (amount * 0.03).toFixed(2);
      }
    }

    if (this.applicationCustomsStepOneGroup.get("collectedDutyAndPenaltyAmount").value == null || this.applicationCustomsStepOneGroup.get("collectedDutyAndPenaltyAmount").value == 0 || this.applicationCustomsStepOneGroup.get("exporterImporterDutyAmount").value == null) {
      this.applicationCustomsStepOneGroup.value.extraReceivedAmount = 0;
      this.applicationCustomsStepOneGroup.value.rewardableAmount = 0;
    }


    this.nbrWelfareGroup.value.appliedRewardAmount = this.rewardCategory[3].amount;
    this.officeWelfareGroup.value.appliedRewardAmount = this.rewardCategory[2].amount;
    var dataArray = [];
    dataArray.push(this.informerGroup.value);
    dataArray.push(this.nbrWelfareGroup.value);
    dataArray.push(this.officeWelfareGroup.value);

    dataArray.forEach((f) => {
      var split = this.getBanglaDigitFromEnglish(f.appliedRewardAmount).split('.');
      f.appliedRewardAmount = split[0];
    })

    this.dataset2 = dataArray;

    if (this.activateRoute.snapshot.paramMap.get("oid") !== null) {
      this.temp.rewardableAmount = parseFloat(this.temp.rewardableAmount)
    } else {
      this.rewardableAmount = parseFloat(this.rewardableAmount);
      this.tempAmount = parseFloat(this.getEnglishDigitFromBangla(this.informerGroup.get("appliedRewardAmount").value))
    }


    if (isNaN(this.rewardableAmount)) {
      this.rewardableAmount = 0;
    }
    if (this.temp != undefined) {
      if (isNaN(this.temp.rewardableAmount)) {
        this.temp.rewardableAmount = 0;
      }
    }


  }

  /*
    this onCheck() function is triggered when checkbox is clicked and from parameted we can get the current toggleStatus.
    Initially toggleStatus is 'false', when user clicks the checkbox, then 'false' is passed through function parameter.
    So, the trick here is,
                         at first checkbox value is 'false' (unchacked), then, user clicks the checkbox 'false' is passed
    by function parameter. As user clicks on the checkbox we make toggleStatus value 'true'. If user again uncheck the
    checkbox previous 'true' value is passed and by an if condition we change toggleStatus value to 'false'. This is how
    checkbox is managed by toggleStatus variable.

    But for draft application toggleStatus value is set from resposne data retrieved from server.

    So, here if toggleStatus is 'true' that means the informer is getting rewarded. So for toggleStatus == 'true' we
    make informer's 'isRewarded' value 'true' and for toggleStatus 'false' we do the opposite.
    */
  handleCheckbox($event: any) {
    if ($event.target.checked == true) {
      this.toggleStatus = false;
    } else {
      this.toggleStatus = true;
    }
    this.officerAmounts = [];
    this.totalOfficerAmount = 0;
    this.rewardCategory[1].amount = 0;
    if (this.toggleStatus) {
      this.informerGroup.get('appliedRewardAmount').setValue('0');
      this.informerGroup.value.isRewarded = "False";
      this.tempAmount = 0;
    } else if (!this.toggleStatus) {
      this.informerGroup.get('appliedRewardAmount').setValue('0');
      this.informerGroup.value.isRewarded = "True";
    }
    this.calculate();
  }

  getApplicationCustomsStepOneDetailsByOid() {
    this.isLoading = true;
    this.applicationService
      .getApplicationCustomsStepOneOid(
        this.activateRoute.snapshot.params["oid"]
      )
      .subscribe(
        (res) => {
          if (res.status === 200) {
            this.sessionList = res.body;
            this.temp = res.body.applicationCustomsStepOneDto;

            if (this.temp.extraReceivedAmount == null) {
              this.temp.extraReceivedAmount = 0;
            }

            this.temp1 = res.body.applicationCustomsStepTwoDto;
            let tempRewardAmounts: [] = res.body.applicationCustomsStepOneDto.rewardAmounts;
            this.setDataset(tempRewardAmounts);
            for (var i = 0; i < tempRewardAmounts.length; i++) {
              if (
                res.body.applicationCustomsStepOneDto.rewardAmounts[i]
                  .designation === "তথ্য সরবরাহকারী" &&
                res.body.applicationCustomsStepOneDto.rewardAmounts[i]
                  .isRewarded === "True"
              ) {
                this.toggleStatus = true;
              }
            }



            this.setFormValue();
            for (let i = 0; i < this.temp.rewardAmounts.length; i++) {
              if (
                ![
                  "কাস্টমস হাউস এর কল্যাণ তহবিল",
                  "জারাবো এর কল্যাণ তহবিল",
                  "তথ্য সরবরাহকারী",
                ].includes(this.temp.rewardAmounts[i].designation)
              ) {
                this.officerAmounts.push(this.temp.rewardAmounts[i]);
              }
              if (
                ["তথ্য সরবরাহকারী"].includes(
                  this.temp.rewardAmounts[i].designation
                )
              ) {
                this.informerGroup.patchValue({
                  appliedRewardAmount: this.temp.rewardAmounts[i].appliedRewardAmount != null ? this.getBanglaDigitFromEnglish(this.temp.rewardAmounts[i].appliedRewardAmount.toString()) : this.temp.rewardAmounts[i].appliedRewardAmount,
                  isRewarded: this.temp.rewardAmounts[i].isRewarded,
                });
              }
            }
            this.tempAmount = this.informerGroup.get('appliedRewardAmount').value == '০' ? 0 : parseFloat(this.getEnglishDigitFromBangla(this.informerGroup.get('appliedRewardAmount').value));

            this.dataset.forEach((amount) => {
              this.distributedAmount += parseFloat(this.getEnglishDigitFromBangla(amount.appliedRewardAmount));
            });

            if (this.officerAmounts.length > 0) {
              this.isTableAvailable = true;
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
        () => {
          this.isLoading = false;
          this.CheckIfAlreadyExistBillOfEntry(this.applicationCustomsStepOneGroup.get("billOfEntryNo").value, this.applicationCustomsStepOneGroup.get("officeOid").value, this.applicationCustomsStepOneGroup.get("billOfEntryDate").value);
          this.calculate();
        }
      );
  }

  setDataset(rewardAmounts: any = []) {
    for (let i = 0; i < rewardAmounts.length; i++) {
      if (
        ![
          "কাস্টমস হাউস এর কল্যাণ তহবিল",
          "জারাবো এর কল্যাণ তহবিল",
          "তথ্য সরবরাহকারী",
        ].includes(rewardAmounts[i].designation)
      ) {
        let appliedRewardAmountcheck = rewardAmounts[i].appliedRewardAmount == null ? "" : rewardAmounts[i].appliedRewardAmount.toString();

        rewardAmounts[i].appliedRewardAmount = this.getBanglaDigitFromEnglish(appliedRewardAmountcheck);
        // rewardAmounts[i].appliedRewardAmount = this.getBanglaDigitFromEnglish(appliedRewardAmountcheck);
        // rewardAmounts[i].employeeName = rewardAmounts[i].employeeName +','+ rewardAmounts[i].designation;
        rewardAmounts[i].employeeName = rewardAmounts[i].employeeName;
        this.tempDataset.push(rewardAmounts[i]);

      }
    }
    this.dataset = this.tempDataset;
  }


  setFormValue() {
    let isReward = this.temp.rewardAmounts.filter(i => i.employeeName == 'তথ্য সরবরাহকারী');

    if (isReward[0].appliedRewardAmount == 0 || isReward[0].appliedRewardAmount == null) {

      (document.getElementById("isRewarded") as HTMLInputElement).checked = false;
    } else {

      (document.getElementById("isRewarded") as HTMLInputElement).checked = true;
    }


    this.uploadedFilesName = this.temp.applicationAttachments[0] == undefined ? "" : this.temp.applicationAttachments[0].originalFileName;

    this.attachment = this.temp.applicationAttachments;
    this.applicationCustomsStepOneGroup.patchValue({
      applyingOfficeOid: this.temp.applyingOfficeOid,
      officeName: this.temp.officeName,
      officeAddress: this.temp.officeAddress,
      billOfEntryNo: this.temp.billOfEntryNo,
      officeOid: this.temp.officeOid,
      // The below part modified by arif
      // Reason: accusedCompanyName, accusedCompanyAddress, wasn't showing the saved form.
      accusedCompanyName: this.temp.accusedCompanyName,
      accusedCompanyAddress: this.temp.accusedCompanyAddress,


      // End of modifications.
      billOfEntryDate: this.temp.billOfEntryDate
        ? new Date(this.temp.billOfEntryDate)
        : null,
      anomalyCaptureDate: this.temp.anomalyCaptureDate
        ? new Date(this.temp.anomalyCaptureDate)
        : null,
      referenceNo: this.temp.referenceNo,
      anomalyReportNo: this.temp.anomalyReportNo,
      anomalyReason: this.temp.anomalyReason,
      appealOrderNo: this.temp.appealOrderNo,
      appealOrderDate: this.temp.appealOrderDate
        ? new Date(this.temp.appealOrderDate)
        : null,
      exporterImporterDutyAmount: this.temp.exporterImporterDutyAmount
        == null ? this.temp.exporterImporterDutyAmount : this.getBanglaDigitFromEnglish(this.temp.exporterImporterDutyAmount.toString()),
      // boe: this.temp1.applicationAttachments[0].fileName,
      collectedDutyAndPenaltyAmount: this.temp.collectedDutyAndPenaltyAmount
        == null ? this.temp.collectedDutyAndPenaltyAmount : this.getBanglaDigitFromEnglish(this.temp.collectedDutyAndPenaltyAmount.toString()),
      category: this.temp.category,
      officeHeadsName: this.temp.officeHeadsName,
      remarksFromCustomsOffice: this.temp.remarksFromCustomsOffice,
      rewardableAmount: this.temp.rewardableAmount,
      extraReceivedAmount: this.temp.extraReceivedAmount == null ? '' : this.temp.extraReceivedAmount,
    });

  }

  prevPage() {
    this.router.navigate(["application-customs-list"]);
  }

  resetPageApplicationCustomsStepOne() {
    this.ngOnInit();
  }

  forwardToApplicationCustomStepTwo() {
    this.router.navigate([
      `application-customs-step-two/${this.activateRoute.snapshot.paramMap.get(
        "oid"
      )}`,
    ]);
  }

  private toggleStatusCheck() {
    if (this.toggleStatus == false) {
      this.informerGroup.value.isRewarded = "true";
    }
  }


  private hotInstance: Handsontable;

  submitApplicationCustomsStepOne($event) {
    this.isLoading = true;
    if (true) {
      this.toggleStatusCheck();
      this.rewardAmounts = [];
      for (let i = 0; i < this.dataset.length; i++) {
        this.dataset[i].sl = i.toString();
        this.dataset[i].isRewarded = 'True';
        this.rewardAmounts.push(this.dataset[i]);
        this.totalAmount += this.dataset[i].appliedRewardAmount;
      }

      if (this.activateRoute.snapshot.paramMap.get("oid") === null) {
        this.rewardAmounts.push(this.informerGroup.value);
      }
      if (this.activateRoute.snapshot.paramMap.get("oid") !== null) {
        this.rewardAmounts.push(this.informerGroup.value);
      }

      this.nbrWelfareGroup.value.appliedRewardAmount =
        this.rewardCategory[3].amount;

      if (this.activateRoute.snapshot.paramMap.get("oid") !== null) {
        this.officerGroup.value.appliedRewardAmount =
          this.rewardCategory[1].amount;
        //new
        this.rewardAmounts.push(this.nbrWelfareGroup.value);
        this.officeWelfareGroup.value.appliedRewardAmount =
          this.rewardCategory[2].amount;
        this.rewardAmounts.push(this.officeWelfareGroup.value);
      } else {
        this.rewardAmounts.push(this.nbrWelfareGroup.value);
        this.officeWelfareGroup.value.appliedRewardAmount =
          this.rewardCategory[2].amount;
        this.rewardAmounts.push(this.officeWelfareGroup.value);
      }

      this.applicationCustomsStepOneGroup.value.rewardAmounts =
        this.rewardAmounts;
      this.applicationCustomsStepOneGroup.value.applicationAttachments =
        this.attachment;
      if (this.oid === null) {
        this.applicationCustomsStepOneGroup.value.rewardableAmount =
          this.rewardableAmount;
        this.applicationCustomsStepOneGroup.value.extraReceivedAmount =
          this.extraTax;
      }
      this.applicationCustomsStepOneGroup.value.applicationStatus = "Draft";
      this.applicationCustomsStepOneGroup.value.applicationStatusBn = "খসড়া";

    }


    //Setting officeName and address
    if (true) {
      if (
        this.applicationCustomsStepOneGroup.value.applyingOfficeOid !== "others"
      ) {
        for (let i = 0; i < this.applyingOffice.length; i++) {
          if (
            this.applyingOffice[i].oid ===
            this.applicationCustomsStepOneGroup.value.applyingOfficeOid
          ) {
            this.applicationCustomsStepOneGroup.value.officeAddress =
              this.applyingOffice[i].bnaddress2;
            this.applicationCustomsStepOneGroup.value.officeName =
              this.applyingOffice[i].bnname;
            break;
          }
        }
      }

      if (
        $event.currentTarget.id === "submit" &&
        this.activateRoute.snapshot.paramMap.get("oid") === null
      ) {

        if (this.billOfEntryStatusChech == true) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("billOfEntryNo").markAsDirty();
          this.applicationCustomsStepOneGroup.get("billOfEntryNo").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "উক্ত বি/ই ও তারিখ ইতিপূর্বেই আছে।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });
        }

        if (this.uploadedFilesName == '') {
          this.isLoading = false;
          this.messageService.add({
            severity: "error",
            summary: "সংযুক্তি (এজেন্টদের ঘোষণা মোতাবেক বিল অফ এনট্রি) যুক্ত করুন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });
        }


        if (this.applicationCustomsStepOneGroup.get("billOfEntryNo").value.length == 0) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("billOfEntryNo").markAsDirty();
          this.applicationCustomsStepOneGroup.get("billOfEntryNo").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে বি/ই নাম্বার দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("applyingOfficeOid").value.length == 0) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("applyingOfficeOid").markAsDirty();
          this.applicationCustomsStepOneGroup.get("applyingOfficeOid").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে আবেদনকারী দপ্তরের নাম দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("officeOid").value.length == 0) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("officeOid").markAsDirty();
          this.applicationCustomsStepOneGroup.get("officeOid").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে বি/ই অফিস নাম দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("billOfEntryDate").value == null || this.applicationCustomsStepOneGroup.get("billOfEntryDate").value == "") {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("billOfEntryDate").markAsDirty();
          this.applicationCustomsStepOneGroup.get("billOfEntryDate").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে বি/ই তারিখ দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("anomalyCaptureDate").value == null || this.applicationCustomsStepOneGroup.get("anomalyCaptureDate").value == "") {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("anomalyCaptureDate").markAsDirty();
          this.applicationCustomsStepOneGroup.get("anomalyCaptureDate").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে আবেদনের তারিখ দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("referenceNo").value.length == 0) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("referenceNo").markAsDirty();
          this.applicationCustomsStepOneGroup.get("referenceNo").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে প্রেরিতব্য পত্রের স্মারক নম্বর দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("appealOrderNo").value.length == 0) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("appealOrderNo").markAsDirty();
          this.applicationCustomsStepOneGroup.get("appealOrderNo").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে ন্যায় নির্ণয়ন/আপীল/রিভিশন আদেশ নং দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("appealOrderDate").value == null || this.applicationCustomsStepOneGroup.get("appealOrderDate").value == "") {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("appealOrderDate").markAsDirty();
          this.applicationCustomsStepOneGroup.get("appealOrderDate").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে ন্যায় নির্ণয়ন/আপীল/রিভিশন তারিখ দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("exporterImporterDutyAmount").value == 0) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("exporterImporterDutyAmount").markAsDirty();
          this.applicationCustomsStepOneGroup.get("exporterImporterDutyAmount").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে ঘোষণা মোতাবেক আমদানিকারক/রপ্তানিকারকের প্রদেয় শুল্ক-করের পরিমাণ দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("collectedDutyAndPenaltyAmount").value == 0) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("collectedDutyAndPenaltyAmount").markAsDirty();
          this.applicationCustomsStepOneGroup.get("collectedDutyAndPenaltyAmount").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে অনিয়ম/মিথ্যা ঘোষণা উদঘাটিত হওয়ায় আদায়কৃত শুল্ক-কর ও জরিমানার পরিমাণ দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("officeHeadsName").value == 0) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("officeHeadsName").markAsDirty();
          this.applicationCustomsStepOneGroup.get("officeHeadsName").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে দপ্তর প্রধানের নাম দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        }

        if ((this.distributedAmount + this.tempAmount + this.rewardableAmount * .15) > (this.rewardableAmount)) {
          this.isLoading = false;
          this.messageService.add({
            severity: "error",
            summary: "মোট বণ্টনকৃত টাকা পুরস্কারযোগ্য অর্থের পরিমাণ থেকে বেশি হয়েছে।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });
        }



        if (this.isLoading == false || this.billOfEntryStatusChech == true) {
          return;

        }

        for (let j = 0; j < this.dataset.length; j++) {
          if (this.dataset[j].employeeName == null || this.dataset[j].employeeName == '') {
            this.messageService.add({
              severity: "error",
              summary: "অনুগ্রহ করে " + (j + 1) + " নং লাইনে আটককারী কর্মকর্তার নাম দিন।",
              detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
            });
            this.isLoading = false;
            return;
          } else if (this.dataset[j].designation == null || this.dataset[j].designation == '') {
            this.messageService.add({
              severity: "error",
              summary: "অনুগ্রহ করে " + (j + 1) + " নং লাইনে আটককারী কর্মকর্তার পদবী দিন।",
              detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
            });
            this.isLoading = false;
            return;
          } else if (this.dataset[j].appliedRewardAmount == null || this.dataset[j].appliedRewardAmount == '') {
            this.messageService.add({
              severity: "error",
              summary: "অনুগ্রহ করে " + (j + 1) + " নং লাইনে আটককারী কর্মকর্তার অর্থের পরিমাণ দিন।",
              detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
            });
            this.isLoading = false;
            return;
          }

        }

        this.applicationService
          .saveApplicationCustomsStepOneUsingPOST(
            this.applicationCustomsStepOneGroup.value
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
                  this.router.navigate([
                    `application-customs-step-two/${res.body.oid}`,
                  ]);
                }, 2000);
              }
            },
            (err) => {
              this.isLoading = false;
              if (err.error && err.error.message) {
                this.messageService.add({
                  severity: "error",
                  summary: "অনুগ্রহ পূর্বক প্রয়োজনীয় তথ্য প্রদান করুন",
                  detail: "",
                });
              }
            }
          );
      } else if (
        $event.currentTarget.id === "draft" &&
        this.activateRoute.snapshot.paramMap.get("oid") === null
      ) {
        this.applicationService
          .saveApplicationCustomsStepOneUsingPOST(
            this.applicationCustomsStepOneGroup.value
          )
          .subscribe(
            (res) => {
              if (res.status === 200) {
                this.messageService.add({
                  severity: "success",
                  summary: "খসড়া সংরক্ষিত হয়েছে",
                  detail: "",
                });
                setTimeout(() => {
                  this.router.navigate([`application-customs-list`]);
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
            }
          );
      } else if (
        $event.currentTarget.id === "draft" &&
        this.activateRoute.snapshot.paramMap.get("oid") !== null
      ) {

        this.isLoading = true;

        this.applicationCustomsStepOneGroup.value.oid = this.activateRoute.snapshot.paramMap.get("oid");

        this.applicationService
          .saveApplicationCustomsStepOneUsingPUT(
            this.applicationCustomsStepOneGroup.value,
            this.activateRoute.snapshot.paramMap.get("oid")
          )
          .subscribe(
            (res) => {
              if (res.status === 200) {
                this.messageService.add({
                  severity: "success",
                  summary: "খসড়া সংরক্ষিত হয়েছে",
                  detail: "",
                });
                setTimeout(() => {
                  this.isLoading = true;
                  this.router.navigate([`application-customs-list`]);
                }, 3000);
              }
            },
            (err) => {
              this.isLoading = false;
              if (err.error && err.error.message) {
                this.messageService.add({
                  severity: "error",
                  summary: "অনুগ্রহ পূর্বক প্রয়োজনীয় তথ্য প্রদান করুন",
                  detail: "",
                });
              }
            },
            () => {

            }
          );

      } else if ($event.currentTarget.id === "submit" && this.activateRoute.snapshot.paramMap.get("oid") !== null) {
        this.applicationCustomsStepOneGroup.value.oid = this.activateRoute.snapshot.paramMap.get("oid");
        this.isLoading = true;

        if (this.billOfEntryStatusChech == true) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("billOfEntryNo").markAsDirty();
          this.applicationCustomsStepOneGroup.get("billOfEntryNo").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "উক্ত বি/ই ও তারিখ ইতিপূর্বেই আছে।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });
        }

        if (this.uploadedFilesName == '') {
          this.isLoading = false;
          this.messageService.add({
            severity: "error",
            summary: "সংযুক্তি (পূর্ববর্তী বিল অফ এনট্রি) যুক্ত করুন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });
        }

        if (this.applicationCustomsStepOneGroup.get("billOfEntryNo").value.length == 0) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("billOfEntryNo").markAsDirty();
          this.applicationCustomsStepOneGroup.get("billOfEntryNo").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে বি/ই নাম্বার দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("applyingOfficeOid").value.length == 0) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("applyingOfficeOid").markAsDirty();
          this.applicationCustomsStepOneGroup.get("applyingOfficeOid").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে আবেদনকারী দপ্তরের নাম দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("officeOid").value.length == 0) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("officeOid").markAsDirty();
          this.applicationCustomsStepOneGroup.get("officeOid").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে বি/ই অফিস নাম দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("billOfEntryDate").value == null) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("billOfEntryDate").markAsDirty();
          this.applicationCustomsStepOneGroup.get("billOfEntryDate").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে বি/ই তারিখ দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("anomalyCaptureDate").value == null) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("anomalyCaptureDate").markAsDirty();
          this.applicationCustomsStepOneGroup.get("anomalyCaptureDate").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে আবেদনের তারিখ দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("referenceNo").value.length == 0) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("referenceNo").markAsDirty();
          this.applicationCustomsStepOneGroup.get("referenceNo").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে প্রেরিতব্য পত্রের স্মারক নম্বর দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("appealOrderNo").value.length == 0) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("appealOrderNo").markAsDirty();
          this.applicationCustomsStepOneGroup.get("appealOrderNo").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে ন্যায় নির্ণয়ন/আপীল/রিভিশন আদেশ নং দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("appealOrderDate").value == null) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("appealOrderDate").markAsDirty();
          this.applicationCustomsStepOneGroup.get("appealOrderDate").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে ন্যায় নির্ণয়ন/আপীল/রিভিশন তারিখ দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("exporterImporterDutyAmount").value == 0) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("exporterImporterDutyAmount").markAsDirty();
          this.applicationCustomsStepOneGroup.get("exporterImporterDutyAmount").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে ঘোষণা মোতাবেক আমদানিকারক/রপ্তানিকারকের প্রদেয় শুল্ক-করের পরিমাণ দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("collectedDutyAndPenaltyAmount").value == 0) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("collectedDutyAndPenaltyAmount").markAsDirty();
          this.applicationCustomsStepOneGroup.get("collectedDutyAndPenaltyAmount").markAsTouched();
          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে অনিয়ম/মিথ্যা ঘোষণা উদঘাটিত হওয়ায় আদায়কৃত শুল্ক-কর ও জরিমানার পরিমাণ দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if (this.applicationCustomsStepOneGroup.get("officeHeadsName").value == 0) {
          this.isLoading = false;
          this.applicationCustomsStepOneGroup.get("officeHeadsName").markAsDirty();
          this.applicationCustomsStepOneGroup.get("officeHeadsName").markAsTouched();

          this.messageService.add({
            severity: "error",
            summary: "অনুগ্রহ করে দপ্তর প্রধানের নাম দিন।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });

        } if ((this.distributedAmount + this.tempAmount + this.rewardableAmount * .15) > (this.temp?.rewardableAmount)) {
          this.isLoading = false;
          this.messageService.add({
            severity: "error",
            summary: "মোট বণ্টনকৃত টাকা পুরস্কারযোগ্য অর্থের পরিমাণ থেকে বেশি হয়েছে।",
            detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
          });
        }

        if (this.isLoading == false || this.billOfEntryStatusChech == true) {
          return;

        }

        for (let j = 0; j < this.dataset.length; j++) {
          if (this.dataset[j].employeeName == null || this.dataset[j].employeeName == '') {
            this.messageService.add({
              severity: "error",
              summary: "অনুগ্রহ করে " + (j + 1) + " নং লাইনে আটককারী কর্মকর্তার নাম দিন।",
              detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
            });
            this.isLoading = false;
            return;
          } else if (this.dataset[j].designation == null || this.dataset[j].designation == '') {
            this.messageService.add({
              severity: "error",
              summary: "অনুগ্রহ করে " + (j + 1) + " নং লাইনে আটককারী কর্মকর্তার পদবী দিন।",
              detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
            });
            this.isLoading = false;
            return;
          } else if (this.dataset[j].appliedRewardAmount == null || this.dataset[j].appliedRewardAmount == '') {
            this.messageService.add({
              severity: "error",
              summary: "অনুগ্রহ করে " + (j + 1) + " নং লাইনে আটককারী কর্মকর্তার অর্থের পরিমাণ দিন।",
              detail: "রিওয়ার্ড আবেদন প্রক্রিয়া",
            });
            this.isLoading = false;
            return;
          }

        }

        this.applicationService
          .saveApplicationCustomsStepOneUsingPUT(
            this.applicationCustomsStepOneGroup.value,
            this.activateRoute.snapshot.paramMap.get("oid")
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
                  this.router.navigate([
                    `application-customs-step-two/${res.body.oid}`,
                  ]);
                }, 3000);
              }
            },
            (err) => {
              this.isLoading = false;
              if (err.error && err.error.message) {
                this.messageService.add({
                  severity: "error",
                  summary: "অনুগ্রহ পূর্বক প্রয়োজনীয় তথ্য প্রদান করুন",
                  detail: "",
                });
              }
            }
          );
      }

    } else {

      this.isLoading = false;
      this.messageService.add({
        severity: "error",
        summary: "অনুগ্রহ পূর্বক প্রয়োজনীয় তথ্য প্রদান করুন",
        detail: "",
      });
    }



  }

  printPreviewApplicationCustomsStepOne() {
    window.print();
  }

  private tempApplyingOfficeforInvestigation = [];
  getApplyingOfficeList(): void {

    this.applicationService.getOfficeList().subscribe(
      (res) => {
        if (res.status === 200) {
          res.body.forEach((option) => {
            if (option.bnname == this.sharedService.getCurrentOfficeName()) {
              this.applyingOffice = res.body;

            } else if (this.rmsRole === 'role-nbr-investigation') {
              this.applyingOffice = [];
              this.applyingOffice = res.body;
              //     this.applyingOffice[8] = {
              //   oid: "others",
              //   bnname: "অন্যান্য",
              // };
            }


          })

          // this.applyingOffice = res.body;
          // this.applyingOffice[6] = {
          //   oid: "others",
          //   bnname: "অন্যান্য",
          // };
        }
      },
      (err) => {
        if (err.status === 404) {
          this.applyingOffice = [];
        }
        if (err.error && err.error.message) {
        }
      },
      () => { }
    );
  }

  getInvestigatorOfficeList(): void {
    this.applicationService.getOfficeList().subscribe(
      (res) => {
        if (res.status === 200) {
          this.investigatorOffice = res.body;
          let temp = [];
          res.body.forEach((entity) => {

            if (entity.oid != '302') {
              temp.push(entity);
            }

          });

          this.investigatorOffice2 = temp;


        }
      },
      (err) => {
        if (err.status === 404) {
          this.investigatorOffice = [];
        }
        if (err.error && err.error.message) {
        }
      },
      () => { }
    );
  }

  generate() {
    if (this.rewardCategory[1].amount > 0) {
      if (
        this.data === undefined ||
        this.data === null ||
        this.data === "" ||
        this.data.length === 0
      ) {
        this.messageService.add({
          severity: "error",
          summary: "সঠিক ফরম্যাটে তথ্য দিন",
          detail: "",
        });
        this.isLoading = false;
        this.displayModal = false;
        return;
      }
      let lines = this.data.split(/\r?\n/);

      this.isLoading = true;

      for (let i = 0; i < lines.length; i++) {
        debugger
        let wordsArray = lines[i].split(/\s{1,}|\t/);
        for (let j = 0; j < wordsArray.length; j++) {
          if (wordsArray[j] === "") {
            wordsArray.splice(j, 1);
          }
        }
        let money = wordsArray.pop();
        let commaRemoved = money.replace(/,/g, "");
        let convertedMoney = this.getEnglishDigitFromBangla(commaRemoved);
        let nameWithDesignationArray = wordsArray.join(" ");
        let nameWithDesignation = nameWithDesignationArray.split(",");
        let designation = nameWithDesignation.pop();
        let name = nameWithDesignation.join(",");

        let obj = {
          advancePaidAmount: 0,
          appliedRewardAmount: parseFloat(convertedMoney),
          designation: designation,
          employeeName: name,
          isRewarded: "True",
        };
        if (obj.appliedRewardAmount > this.rewardCategory[1].amount) {
          this.messageService.add({
            severity: "error",
            summary:
              "আবেদনকৃত অর্থের পরিমাণ এবং পুরস্কারযোগ্য অর্থের পরিমাণ সমান নয় ",
            detail: "",
          });
          return;
        } else if (
          this.totalOfficerAmount +
          this.informerGroup.value.appliedRewardAmount >
          this.rewardCategory[1].amount
        ) {
          this.messageService.add({
            severity: "error",
            summary:
              "আবেদনকৃত অর্থের পরিমাণ এবং পুরস্কারযোগ্য অর্থের পরিমাণ সমান নয় ",
            detail: "",
          });
          return;
        } else if (
          this.totalOfficerAmount +
          this.informerGroup.value.appliedRewardAmount +
          this.officerAmounts.appliedRewardAmount >
          this.rewardCategory[1].amount
        ) {
          this.messageService.add({
            severity: "error",
            summary:
              "আবেদনকৃত অর্থের পরিমাণ এবং পুরস্কারযোগ্য অর্থের পরিমাণ সমান নয় ",
            detail: "",
          });
          return;
        } else {
          this.officerAmounts.push(obj);
          this.totalOfficerAmount += obj.appliedRewardAmount;
          if (this.officerAmounts.length > 0) {
            this.isTableAvailable = true;
          }
        }
      }
    } else {
      this.messageService.add({
        severity: "error",
        summary: "অনুগ্রহপূর্বক পূর্ববর্তী তথ্য প্রদান করুন",
        detail: "",
      });
      this.isLoading = false;
      this.displayModal = false;
      this.data = "";
      return;
    }
    this.totalOfficerAmount += this.officerAmounts.appliedRewardAmount;
    this.isLoading = false;
    this.displayModal = false;

    if (this.officerAmounts.length > 0) {
      this.isTableAvailable = true;
    }
    this.data = "";
  }

  addToOfficerList() {
    if (this.rewardCategory[1].amount > 0) {
      this.calculate();
      this.calculateInformer();
      if (this.officerGroup.value.advancePaidAmount === "") {
        this.officerGroup.value.advancePaidAmount = 0;
      }
      let addToListObj = this.officerGroup.value;
      if (!this.checkDataExistsValidation(addToListObj)) {
        return;
      }
      this.officerAmounts.push(addToListObj);
      if (!this.isTableAvailable) {
        this.isTableAvailable = !this.isTableAvailable;
      }
      this.resetOfficerGroup();
    } else {
      this.messageService.add({
        severity: "error",
        summary: "অনুগ্রহপূর্বক পূর্ববর্তী তথ্য প্রদান করুন",
        detail: "",
      });
      return;
    }
  }

  calculateInformer() {
    if (this.toggleStatus === true) {

    } else {
      this.informerGroup.value.appliedRewardAmount = 0;
    }
  }
  checkDataExistsValidation(pobj: any) {
    let isValid = true;
    if (!pobj.employeeName) {
      this.messageService.add({
        severity: "error",
        summary: "নাম লিখুন",
        detail: "",
      });
      isValid = false;
      return;
    }

    if (!pobj.designation) {
      this.messageService.add({
        severity: "error",
        summary: "পদবী লিখুন",
        detail: "",
      });
      isValid = false;
      return;
    }

    if (!pobj.appliedRewardAmount) {
      this.messageService.add({
        severity: "error",
        summary: "আবেদনকৃত অর্থের পরিমাণ লিখুন",
        detail: "",
      });
      isValid = false;
      return;
    }

    this.officerAmounts.forEach((existsData: any) => {
      if (existsData.employeeName == pobj.employeeName) {
        this.messageService.add({
          severity: "error",
          summary: "আটককারী কর্মকর্তা / কর্মচারীর নাম একাধিকবার দেয়া যাবে না",
          detail: "",
        });
        isValid = false;
        return;
      }
    });
    if (
      pobj.appliedRewardAmount + this.informerGroup.value.appliedRewardAmount >
      this.rewardCategory[1].amount
    ) {
      this.messageService.add({
        severity: "error",
        summary:
          "আবেদনকৃত অর্থের পরিমাণ এবং পুরস্কারযোগ্য অর্থের পরিমাণ সমান নয় ",
        detail: "",
      });
      isValid = false;
      return;
    } else if (
      this.totalOfficerAmount +
      pobj.appliedRewardAmount +
      this.informerGroup.value.appliedRewardAmount >
      this.rewardCategory[1].amount
    ) {
      this.messageService.add({
        severity: "error",
        summary:
          "আবেদনকৃত অর্থের পরিমাণ এবং পুরস্কারযোগ্য অর্থের পরিমাণ সমান নয় ",
        detail: "",
      });
      isValid = false;
      return;
    } else {
      this.totalOfficerAmount += pobj.appliedRewardAmount;
    }
    return isValid;
  }

  officerRemove($index) {
    this.totalOfficerAmount =
      this.totalOfficerAmount - this.officerAmounts[$index].appliedRewardAmount;
    this.officerAmounts.splice($index, 1);
    if (this.officerAmounts.length === 0) {
      {
        this.isTableAvailable = false;
      }
    }
  }


  private uploadedFile: any[];
  private uploadedFileType: any;
  fileSelected(event: any, fileType: string) {


    if (event.target.files[0].size > 10000000) {
      this.messageService.add({
        severity: "error",
        summary: "১০ মেগাবাইট এর বেশি হয়েছে।",
        detail: "আপলোড",
      });
      return;
    }
    let files: any[] = [];
    files = event.target.files;

    this.uploadedFile = files
    this.uploadedFilesName = files[0].name;
    this.uploadedFileType = fileType;
    const fileExt = files[0].name.split(".").pop();

    if (!["pdf", "PDF", "jpg", "jpeg", "JPEG", "JPG", "png", "PNG"].includes(fileExt)) {
      this.messageService.add({
        severity: "error",
        summary: "Please select only pdf or jpg/png",
        detail: "",
      });
      return;
    } else {
      // this.stepOneBOEFileProgress = 100;
    }
    if (!files) {
      return;
    }





    const url = `${resourceServerUrl}/v1/file/upload`;
    const formData = new FormData();
    formData.append("files", files[0]);
    this.fileService
      .upload(files, url, formData, "boe")
      .subscribe((event: any) => {
        if (event.type === HttpEventType.UploadProgress) {
          // for (let i = 0; i < files.length; i++) {
          this.stepOneBOEFileProgress = Math.round(100 * event.loaded / event.total);
          // console.log(event);
          // }
        } else if (event instanceof HttpResponse) {
          if (event.status === 200) {
            const fileName = event.body.files[0];


            this.realFileName = fileName;

            let fileUrl = "/opt/rms/boe/" + fileName;
            if (fileType === "boe") {
              this.attachment = [
                {
                  // oid: "",
                  // id: "",
                  fileName: fileName,
                  attachmentType: "এজেন্টদের ঘোষণা মোতাবেক বিল অফ এন্ট্রি",
                  fileUrl: fileUrl,
                  originalFileName: this.uploadedFilesName
                },
              ];
            }
          }
        }
      });
  }

  resetOfficerGroup() {
    this.officerGroup = this.rewardAmount.group({
      employeeName: "",
      designation: "",
      advancePaidAmount: "",
      appliedRewardAmount: "",
      isRewarded: "True",
    });
  }

  bulkUpload() {
    this.displayModal = true;
  }

  checkBillOfEntryNo() {
    this.applicationService
      .getBillOfEntryNoList(this.applicationCustomsStepOneGroup.value.billOfEntryNo)
      .subscribe((res) => {
        this.billOfEntryNoCheck = res.body;
      });
  }

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

  getBanglaDigitFromEnglish(inputString: string) {
    let finalEnlishToBanglaNumber = {
      '0': '০', '1': '১', '2': '২', '3': '৩', '4': '৪', '5': '৫', '6': '৬', '7': '৭', '8': '৮', '9': '৯', '': ''
    };
    for (let x in finalEnlishToBanglaNumber) {
      inputString = inputString.replace(
        new RegExp(x, "g"),
        finalEnlishToBanglaNumber[x]
      );
    }
    return inputString;

  }

  // Modified By Arif
  // Reason: To check wether BillOfEntryNo & OfficeName & Date is already exist to the database
  CheckIfAlreadyExistBillOfEntry(billOfEntryNo?: string, officeName?: string, date?: string) {
    if (billOfEntryNo != null && officeName != null && date != "") {
      const dd = formatDate(date, 'yyyy-MM-dd HH:mm:ss', 'en');

      this.applicationService.getBillOfentryIfExists(billOfEntryNo, officeName, dd).subscribe(
        res => {
          this.billOfEntryStatusChech = res.body;
        }
      );
    }
  }



  uploadToDirectory(files: any, fileType: string) {

    if (fileType == "" || fileType == undefined) {
      return;
    } else {
      const url = `${resourceServerUrl}/v1/file/upload`;
      const formData = new FormData();
      formData.append("files", files[0]);
      this.fileService
        .upload(files, url, formData, "boe")
        .subscribe((event: any) => {
          if (event.type === HttpEventType.UploadProgress) {
            // for (let i = 0; i < files.length; i++) {
            this.stepOneBOEFileProgress = 0;
            this.stepOneBOEFileProgress = Math.round(100 * event.loaded / event.total);
            // }
          } else if (event instanceof HttpResponse) {
            if (event.status === 200) {
              const fileName = event.body.files[0];


              this.realFileName = fileName;

              let fileUrl = "/opt/rms/boe/" + fileName;
              if (fileType == "boe") {
                this.attachment = [
                  {
                    oid: "",
                    fileName: fileName,
                    attachmentType: "পূর্ববর্তী বিল অফ এন্ট্রি",
                    fileUrl: fileUrl,
                  },
                ];



              }
            }
          }
        },
          (err) => { console.log(err); this.isLoading = false; },
          () => {
            this.isLoading = false;
          }
        );
    }


  }

  deleteUploadedTempFile() {

    this.deleteFile(this.attachment[0].fileName, "/opt/rms/boe/");

  }

  deleteFile(name: string, fileDir: string) {
    this.isLoading = true;
    const url = `${resourceServerUrl}/v1/file/delete/${name}`;
    this.fileService.deleteUplodedFile(url, fileDir).subscribe(
      res => {

      },
      err => { this.isLoading = false; },
      () => {
        this.uploadedFile = [];
        this.uploadedFileType = '';
        this.uploadedFilesName = '';
        this.stepOneBOEFileProgress = 0;
        this.attachment = null;
        this.isLoading = false;
      }
    );


  }

  private exporterImporterDutyAmount: number = 0;
  private collectedDutyAndPenaltyAmount: number = 0;
  private appliedRewardAmount2: number = 0;


  pInput(event: InputEvent, id: string) {


    if (event.isComposing) {

      if (this.exporterImporterDutyAmount != null && id.match("exporterImporterDutyAmount")) {
        this.exporterImporterDutyAmount = Number.parseInt(this.getEnglishDigitFromBangla(event.data));
      } else if (this.collectedDutyAndPenaltyAmount != null && id.match("collectedDutyAndPenaltyAmount")) {
        this.collectedDutyAndPenaltyAmount = Number.parseInt(this.getEnglishDigitFromBangla(event.data));
      } else if (this.appliedRewardAmount2 != null && id.match("appliedRewardAmount")) {
        this.appliedRewardAmount2 = Number.parseInt(this.getEnglishDigitFromBangla(event.data));
      }
    }

  }

  customAmount(id: string, event: Event) {
    if (id.match("exporterImporterDutyAmount") && this.applicationCustomsStepOneGroup.get("exporterImporterDutyAmount").value == null) {
      this.applicationCustomsStepOneGroup.get("exporterImporterDutyAmount").setValue(this.exporterImporterDutyAmount);
      this.calculate();
    } else if (id.match("collectedDutyAndPenaltyAmount") && this.applicationCustomsStepOneGroup.get("collectedDutyAndPenaltyAmount").value == null) {
      this.applicationCustomsStepOneGroup.get("collectedDutyAndPenaltyAmount").setValue(this.collectedDutyAndPenaltyAmount);
      this.calculate();
    } else if (id.match('appliedRewardAmount') && this.informerGroup.get('appliedRewardAmount').value == null) {

      this.informerGroup.get("appliedRewardAmount").setValue(this.appliedRewardAmount2);

    }
  }


  distributedAmountContol() {
    this.tempAmount = parseFloat(this.getEnglishDigitFromBangla(this.informerGroup.get("appliedRewardAmount").value));
    if (isNaN(this.tempAmount)) {
      this.tempAmount = 0;
    }

  }


  public attachmentStepTwo: ApplicationAttachment[] = [];
  viewPdf() {


    if (this.uploadedFile == undefined) {

      this.nbrApplicationService.getApplicationDetails(this.activateRoute.snapshot.params["oid"]).subscribe(
        (res) => {
          if (res.status === 200) {

            this.isLoading = true
            this.attachmentStepTwo = res.body.applicationCustomsStepOneDto.applicationAttachments;

            this.nbrApplicationService.getStepOneFileDownload(this.attachmentStepTwo[0].fileName).subscribe(
              (res) => {
                if (res.status === 200) {
                  const pdfUrl = URL.createObjectURL(res.body);
                  const popupWin = window.open(pdfUrl, '_blank', 'fullscreen=yes');
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
        () => {


          // const pdfUrl = URL.createObjectURL(this.attachmentStepTwo[0);
          // const popupWin = window.open(pdfUrl, '_blank', 'fullscreen=yes');   
        }
      );
    } else {
      const pdfUrl = URL.createObjectURL(this.uploadedFile[0]);
      const popupWin = window.open(pdfUrl, '_blank', 'fullscreen=yes');
    }
  }

  public addRowtoDataSet() {
    this.dataset = [
      {
        employeeName: "",
        appliedRewardAmount: "",
        designation: "",
        advancePaidAmount: "",
        isRewarded: "True",

      }

    ];

  }


}