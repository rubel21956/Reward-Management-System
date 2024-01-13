import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ApplicationService} from '@app/auth/application/services/application.service';
import {MenuItem, MessageService} from 'primeng/api';
import {ActivatedRoute, Router} from '@angular/router';
import {OfficeService} from '@app/auth/office/services/office.service';
import {SharedService} from '@app/common/services/shared.service';
import {DomSanitizer} from '@angular/platform-browser';
import {ApplicationCustomsStepOne} from '../model/applicationCustomsStepOne';
import {formatDate} from '@angular/common';
import {resourceServerUrl} from '@app/common/constants/server-settings';
import {FileService} from '@app/common/services/file.service';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import {ApplicationCustomsStepTwo} from '@app/auth/application/model/applicationCustomsStepTwo';
import {ApplicationAttachment} from '@app/auth/application/model/applicationAttachment';
import { PrimeNGConfig } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { NbrApplicationService } from '@app/auth/nbr-admin/services/nbr-application.service';
import { param } from 'jquery';


@Component({
  selector: "app-application-customs-step-two",
  templateUrl: "./application-customs-step-two.component.html",
  styleUrls: ["./application-customs-step-two.component.css"],
})
export class ApplicationCustomsStepTwoComponent implements OnInit {
  oid: string = "";
  public isLoading: boolean = false;
  public displayModal = false;
  public confirmModal = false;
  public uploadedFiles: any;
  public sessionList: any[];
  temp: ApplicationCustomsStepTwo;
  temp1: ApplicationCustomsStepOne;
  minDate: Date;
  maxDate: Date;
  applicationCustomsStepTwoGroup: FormGroup;
  applicationCustomsStepOneGroup: ApplicationCustomsStepOne;
  applicationAttachments: any = [];
  public file1: string;
  public file2: string;
  public file3: string;
  public file4: string;
  public file5: string;
  public file6: string;
  public file7: string;
  attachment_01: Array<ApplicationAttachment>;
  attachment_02: Array<ApplicationAttachment>;
  attachment_03: Array<ApplicationAttachment>;
  attachment_04: Array<ApplicationAttachment>;
  attachment_05: Array<ApplicationAttachment>;
  attachment_06: Array<ApplicationAttachment>;
  attachment_07: Array<ApplicationAttachment>;
  submitted: boolean = false;
  public rmsRole = "";

  public pageStatus: any;

  items: MenuItem[];
  activeIndex: number = 1;
  // Modified By ARIF 
  // Reason: To show upload progress data (Same as step one)
  public stepOneBOEFileProgress1: any;
  public stepOneBOEFileProgress2: any;
  public stepOneBOEFileProgress3: any;
  public stepOneBOEFileProgress4: any;
  public stepOneBOEFileProgress5: any;
  public stepOneBOEFileProgress6: any;
  public stepOneBOEFileProgress7: any;

  constructor(
    private nbrApplicationService: NbrApplicationService,
    private applicationCustomsStepTwo: FormBuilder,
    private applicationService: ApplicationService,
    private messageService: MessageService,
    private router: Router,
    private officeService: OfficeService,
    private fileService: FileService,
    private sharedService: SharedService,
    private activateRoute: ActivatedRoute,
    private sanitizer: DomSanitizer,
    private config: PrimeNGConfig,
    private translateService: TranslateService
  ) {
    const today = new Date();
    this.minDate = new Date("January 01, 1900");
    this.maxDate = new Date(today);
  }

  get f() {
    return this.applicationCustomsStepTwoGroup.controls;
  }

  ngOnInit(): void {
    window.scrollTo(0,0);
    this.rmsRole = this.sharedService.getRmsRole();
    if (this.rmsRole == "role-operator" || this.rmsRole == "role-nbr-investigation") {      
       // Step Module Implementation
    this.items = [{
      label: '১ম ধাপ',
      command: (event: any) => {
          this.activeIndex = 0;
          this.messageService.add({severity:'success', summary:'প্রথম ধাপ', detail: event.item.label});
          setTimeout(() => {
            this.router.navigate(['application-customs-step-one', 
            this.activateRoute.snapshot.paramMap.get("oid")])
          }, 2000);
      }
  },
  {
      label: '২য় ধাপ',
      command: (event: any) => {
          this.activeIndex = 1;
          this.messageService.add({severity:'success', summary:'দ্বিতীয় ধাপ', detail: event.item.label});
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
          this.messageService.add({severity:'success', summary:'তৃতীয় ধাপ', detail: event.item.label});
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
          this.messageService.add({severity:'success', summary:'চতুর্থ ধাপ', detail: event.item.label});
          setTimeout(() => {
            this.router.navigate(['application-customs-step-five', 
            this.activateRoute.snapshot.paramMap.get("oid")])
          }, 2000);
      }
  }
];

if(this.activateRoute.snapshot.paramMap.get("oid") === null){
  this.pageStatus = 1;
  } else {
  this.pageStatus = 2;
  }

      this.applicationCustomsStepTwoGroup =
        this.applicationCustomsStepTwo.group({
          oid: [""],
          applicationDate: ["", Validators.required],
          applicationNarration: ["", Validators.required],
          applicationAttachments: this.applicationAttachments,
          applicationStatus: [""],
        });

      this.oid = this.activateRoute.snapshot.params["oid"];
      this.getApplicationCustomsStepOne(
        this.activateRoute.snapshot.params["oid"]
      );
      this.applicationAttachments = [];
      this.attachment_01 = [
        {
          oid: this.oid,
          attachmentType: 'পরিশিষ্ট - "ক" অনুযায়ী পুরস্কার মঞ্জুরীর জন্য তথ্য',
          fileName: "",
          fileUrl: "",
          id: "",
          originalFileName: ""
        },
      ];
      this.attachment_02 = [
        {
          id: "",
          oid: this.oid,
          fileName: "",
          attachmentType: "অনিয়ম বা শুল্ক ফাঁকি সংক্রান্ত মামলার বিবরণী",
          fileUrl: "",
          originalFileName: ""
        },
      ];
      this.attachment_03 = [
        {
          id: "",
          oid: this.oid,
          fileName: "",
          attachmentType: "শুল্ক ফাঁকি মামলার ন্যায় নির্ণয়ের আদেশের কপি",
          fileUrl: "",
          originalFileName: ""
        },
      ];
      this.attachment_04 = [
        {
          id: "",
          oid: this.oid,
          fileName: "",
          attachmentType:
            "পুরস্কার প্রাপ্তির আবেদনের বিষয়টি আদালতে বিচারাধীন নয় মর্মে অফিস প্রধানের প্রত্যায়নপত্র",
          fileUrl: "",
          originalFileName: ""
        },
      ];
      this.attachment_05 = [
        {
          id: "",
          oid: this.oid,
          fileName: "",
          attachmentType:
            "পুরস্কার প্রাপ্তির আবেদনের বিষয়ে ইতোপূর্বে পুরস্কার গ্রহণ করা হয়নি মর্মে অফিস প্রধানের প্রত্যায়নপত্র",
          fileUrl: "",
          originalFileName: ""
        },
      ];
      this.attachment_06 = [
        {
          id: "",
          oid: this.oid,
          fileName: "",
          attachmentType: "ঘোষণা অনুযায়ী প্রদেয় রাজস্স সংক্রান্ত তথ্য",
          fileUrl: "",
          originalFileName: ""
        },
      ];
      this.attachment_07 = [
        {
          id: "",
          oid: this.oid,
          fileName: "",
          attachmentType:
            "আদায়কৃত শুল্ক-কর ও জরিমানা সরকারি কোষাগারে জমার প্রমাণপত্র (জমার সঠিকতা যাচাইকৃত)",
          fileUrl: "",
          originalFileName: ""
        },
      ];
      if (this.activateRoute.snapshot.paramMap.get("oid") !== null) {
        this.getApplicationCustomsStepTwoDetailsByOid();
      }
    } else {
      this.router.navigate(["page-not-found"]);
    }
    this.translate("bn");
  }

  translate(lang: string) {
    this.translateService.use(lang);
    this.translateService
      .get("primeng")
      .subscribe((res) => this.config.setTranslation(res));
  }

  getApplicationCustomsStepTwoDetailsByOid() {
    this.isLoading = true;
    this.applicationService
      .getApplicationCustomsOid(this.activateRoute.snapshot.params["oid"])
      .subscribe(
        (res) => {
          if (res.status === 200) {
            this.sessionList = res.body;
            this.temp = res.body.applicationCustomsStepTwoDto;
            this.temp1 = res.body.applicationCustomsStepOneDto;
            console.log("this is details");
            console.log(res);
            
            for (let i = 0; i < res.body.applicationCustomsStepTwoDto.applicationAttachments.length; i++) {
              let fileName = res.body.applicationCustomsStepTwoDto.applicationAttachments[i].fileName;
              let attachmentType = res.body.applicationCustomsStepTwoDto.applicationAttachments[i].attachmentType;

              if(fileName != null){
                this.downloadForDraftPreview(fileName, attachmentType);    
                console.log(true);
                            
              }  
              console.log(this.uploadedFile1);
              console.log(this.uploadedFile2);
              console.log(this.uploadedFile3);
              console.log(this.uploadedFile4);
              console.log(this.uploadedFile5);
              console.log(this.uploadedFile6);
              console.log(this.uploadedFile7);
                          
            }
            
            this.setFormValue();
            for (let i = 0; i < this.temp.applicationAttachments.length; i++) {
              if (
                this.temp.applicationAttachments[i].attachmentType ===
                'পরিশিষ্ট - "ক" অনুযায়ী পুরস্কার মঞ্জুরীর জন্য তথ্য'
              ) {
                this.file1 = this.temp.applicationAttachments[i].fileName;
                this.attachment_01 = [];
                this.attachment_01.push(this.temp.applicationAttachments[i]);                                                                
              } else if (
                this.temp.applicationAttachments[i].attachmentType ===
                "অনিয়ম বা শুল্ক ফাঁকি সংক্রান্ত মামলার বিবরণী"
              ) {
                this.file2 = this.temp.applicationAttachments[i].fileName;
                this.attachment_02 = [];
                this.attachment_02.push(this.temp.applicationAttachments[i]);
              } else if (
                this.temp.applicationAttachments[i].attachmentType ===
                "শুল্ক ফাঁকি মামলার ন্যায় নির্ণয়ের আদেশের কপি"
              ) {
                this.file3 = this.temp.applicationAttachments[i].fileName;
                this.attachment_03 = [];
                this.attachment_03.push(this.temp.applicationAttachments[i]);
              } else if (
                this.temp.applicationAttachments[i].attachmentType ===
                "পুরস্কার প্রাপ্তির আবেদনের বিষয়টি আদালতে বিচারাধীন নয় মর্মে অফিস প্রধানের প্রত্যায়নপত্র"
              ) {
                this.file4 = this.temp.applicationAttachments[i].fileName;
                this.attachment_04 = [];
                this.attachment_04.push(this.temp.applicationAttachments[i]);
              } else if (
                this.temp.applicationAttachments[i].attachmentType ===
                "পুরস্কার প্রাপ্তির আবেদনের বিষয়ে ইতোপূর্বে পুরস্কার গ্রহণ করা হয়নি মর্মে অফিস প্রধানের প্রত্যায়নপত্র"
              ) {
                this.file5 = this.temp.applicationAttachments[i].fileName;
                this.attachment_05 = [];
                this.attachment_05.push(this.temp.applicationAttachments[i]);
              } else if (
                this.temp.applicationAttachments[i].attachmentType ===
                "ঘোষণা অনুযায়ী প্রদেয় রাজস্স সংক্রান্ত তথ্য"
              ) {
                this.file6 = this.temp.applicationAttachments[i].fileName;
                this.attachment_06 = [];
                this.attachment_06.push(this.temp.applicationAttachments[i]);
              } else if (
                this.temp.applicationAttachments[i].attachmentType ===
                "আদায়কৃত শুল্ক-কর ও জরিমানা সরকারি কোষাগারে জমার প্রমাণপত্র (জমার সঠিকতা যাচাইকৃত)"
              ) {
                this.file7 = this.temp.applicationAttachments[i].fileName;
                this.attachment_07 = [];
                this.attachment_07.push(this.temp.applicationAttachments[i]);
              }
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
        }
      );
  }

  setFormValue() {
    this.applicationCustomsStepTwoGroup.patchValue({
      applicationDate: this.temp.applicationDate
        ? new Date(this.temp.applicationDate)
        : null,
      applicationNarration: this.temp.applicationNarration,
    });
  }

  confirmationApplicationCustomsStepTwo($event) {
    if (
      this.attachment_01[0]?.fileName == "" ||
      this.attachment_02[0]?.fileName == "" ||
      this.attachment_03[0]?.fileName == "" ||
      this.attachment_04[0]?.fileName == "" ||
      this.attachment_05[0]?.fileName == "" ||
      this.attachment_06[0]?.fileName == "" ||
      this.attachment_07[0]?.fileName == "" ||
      this.applicationAttachments == null
    ) {
      this.displayModal = true;
    } else {
      this.submitApplicationCustomsStepTwo($event);
    }
  }

  notCompletedApplicationCustomsStepTwo() {
    if (
      this.temp.applicationAttachments[0]?.fileName == null ||
      this.temp.applicationAttachments[1]?.fileName == null ||
      this.temp.applicationAttachments[2]?.fileName == null ||
      this.temp.applicationAttachments[3]?.fileName == null ||
      this.temp.applicationAttachments[4]?.fileName == null ||
      this.temp.applicationAttachments[5]?.fileName == null ||
      this.temp.applicationAttachments[6]?.fileName == null ||
      this.applicationAttachments == null
    ) {
      this.confirmModal = true;
    } else {
      this.prevToApplicationCustomsStepOne();
    }
  }

  confirmStay() {
    this.displayModal = false;
    this.confirmModal = false;
  }

  forwardToApplicationCustomsStepFour() {
    this.router.navigate([
      `application-customs-step-four/${this.activateRoute.snapshot.paramMap.get(
        "oid"
      )}`,
    ]);
  }

  submitApplicationCustomsStepTwo($event) {
    this.isLoading = true;
    if (
      this.attachment_01 != null &&
      this.attachment_01[0].fileName.length > 0
    ) {      
      this.applicationAttachments.push(this.attachment_01[0]);
    }
    if (
      this.attachment_02 != null &&
      this.attachment_02[0].fileName.length > 0
    ) {
      this.applicationAttachments.push(this.attachment_02[0]);
    }
    if (
      this.attachment_03 != null &&
      this.attachment_03[0].fileName.length > 0
    ) {
      this.applicationAttachments.push(this.attachment_03[0]);
    }
    if (
      this.attachment_04 != null &&
      this.attachment_04[0].fileName.length > 0
    ) {
      this.applicationAttachments.push(this.attachment_04[0]);
    }
    if (
      this.attachment_05 != null &&
      this.attachment_05[0].fileName.length > 0
    ) {
      this.applicationAttachments.push(this.attachment_05[0]);
    }
    if (
      this.attachment_06 != null &&
      this.attachment_06[0].fileName.length > 0
    ) {
      this.applicationAttachments.push(this.attachment_06[0]);
    }
    if (
      this.attachment_07 != null &&
      this.attachment_07[0].fileName.length > 0
    ) {
      this.applicationAttachments.push(this.attachment_07[0]);
    }
    this.applicationCustomsStepTwoGroup.value.oid =
      this.activateRoute.snapshot.params["oid"];
    this.applicationCustomsStepTwoGroup.value.applicationStatus = "Draft";
    this.applicationCustomsStepTwoGroup.value.applicationStatusBn = "খসড়া";
    this.applicationCustomsStepTwoGroup.value.applicationAttachments = this.applicationAttachments;
    if (true) {
      if (
        $event.currentTarget.id === "submit" &&
        this.activateRoute.snapshot.paramMap.get("oid") !== null && this.applicationCustomsStepTwoGroup.valid
      ) {
        console.log(this.applicationCustomsStepTwoGroup.value);
        
        this.applicationService
          .updateApplicationCustomsStepTwoUsingPUT(
            this.applicationCustomsStepTwoGroup.value,
            this.activateRoute.snapshot.paramMap.get("oid")
          )
          .subscribe(
            (res) => {
              if (res.status === 200) {
                console.log('Step two put 1');
                console.log(res);
                
                this.messageService.add({
                  severity: "success",
                  summary: "ধাপ-২ সফল ভাবে সম্পন্ন হয়েছে",
                  detail: "",
                });
                setTimeout(() => {
                  this.router.navigate([
                    `application-customs-step-four/${res.body.oid}`], { queryParams: { height: this.height2 } });
                }, 3000);
              }
            },
            (err) => {
              this.isLoading = false;
              if (err.error && err.error.message) {
                this.messageService.add({
                  severity: "error",
                  summary: 'অনুগ্রহ করে তারিখ ও বিবরণ দিন।',
                  detail: "পরবর্তী ধাপ",
                });
              }
            }
          );
      } else if (
        $event.currentTarget.id === "draft" &&
        this.activateRoute.snapshot.paramMap.get("oid") !== null
      ) {
        console.log(this.applicationCustomsStepTwoGroup.value);
        this.applicationService
          .updateApplicationCustomsStepTwoUsingPUT(
            this.applicationCustomsStepTwoGroup.value,
            this.activateRoute.snapshot.paramMap.get("oid")
          )
          .subscribe(
            (res) => {
              console.log('Step two put 2');
                console.log(res);
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
      }
     else {
      // this.applicationAttachments = [];
      this.isLoading = false;
      this.messageService.add({
        severity: "error",
        summary: 'অনুগ্রহ করে তারিখ ও বিবরণ দিন।',
        detail: "পরবর্তী ধাপ",
      });
    }
    }
  }

  printPreviewApplicationCustomsStepTwo() {
    window.print();
  }

  prevToApplicationCustomsStepOne() {
    this.router.navigate(
      [
        "/application-customs-step-one",
        this.activateRoute.snapshot.params["oid"],
      ],
      { relativeTo: this.activateRoute }
    );
  }

  getApplicationCustomsStepOne(oid: string) {
    this.isLoading = true;
    this.applicationService.getApplicationCustomsStepOneOid(oid).subscribe(
      (res) => {
        if (res.status === 200) {
          this.applicationCustomsStepOneGroup = res.body;
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
  }

  private uploadedFile1: any;
  private uploadedFile2: any;
  private uploadedFile3: any;
  private uploadedFile4: any;
  private uploadedFile5: any;
  private uploadedFile6: any;
  private uploadedFile7: any;
  viewPdf(fileType: string){
    if(fileType == '01'){
      this.showPdf(this.uploadedFile1);
    }else if(fileType == '02'){
      this.showPdf(this.uploadedFile2);
    }else if(fileType == '03'){
      this.showPdf(this.uploadedFile3);
    }else if(fileType == '04'){
      this.showPdf(this.uploadedFile4);
    }else if(fileType == '05'){
      this.showPdf(this.uploadedFile5);
    }else if(fileType == '06'){
      this.showPdf(this.uploadedFile6);
    }else if(fileType == '07'){
      this.showPdf(this.uploadedFile7);
    }
    
  }

  showPdf(file: any){
    const pdfUrl = URL.createObjectURL(file);
    const popupWin = window.open(pdfUrl, '_blank', 'fullscreen=yes');
  }
  fileSelected(target: any, fileType: string) {
    if(target.files[0].size > 10000000){
      this.messageService.add({
        severity: "error",
        summary: "১০ মেগাবাইট এর বেশি হয়েছে।",
        detail: "আপলোড",
      });
      return;
    }
    let files: any[] = [];
    files = target.files;
    const fileExt = files[0].name.split(".").pop();
    if (!["pdf", "PDF", "jpg", "jpeg", "JPEG", "JPG", "png", "PNG"].includes(fileExt)) {
      this.messageService.add({
        severity: "error",
        summary: "Please select only pdf or jpg/png",
        detail: "",
      });
      return;
    }
    if (!files) {
      return;
    } 
        
    const url = `${resourceServerUrl}/v1/file/upload`;
    const formData = new FormData();
    formData.append("files", files[0]);
    this.fileService
      .upload(files, url, formData, "appendix")
      .subscribe((event: any) => {        
        if (event.type === HttpEventType.UploadProgress) {
          this.isLoading = true;
          for (let i = 0; i < files.length; i++) {
            //   this.files[i].progress = Math.round(100 * event.loaded / event.total);
            if(fileType == "01"){this.stepOneBOEFileProgress1 = Math.round(100 * event.loaded / event.total);}
            else if(fileType == "02"){this.stepOneBOEFileProgress2 = Math.round(100 * event.loaded / event.total);}
            else if(fileType == "03"){this.stepOneBOEFileProgress3 = Math.round(100 * event.loaded / event.total);}
            else if(fileType == "04"){this.stepOneBOEFileProgress4 = Math.round(100 * event.loaded / event.total);}
            else if(fileType == "05"){this.stepOneBOEFileProgress5 = Math.round(100 * event.loaded / event.total);}
            else if(fileType == "06"){this.stepOneBOEFileProgress6 = Math.round(100 * event.loaded / event.total);}
            else if(fileType == "07"){this.stepOneBOEFileProgress7 = Math.round(100 * event.loaded / event.total);}
          }
        } else if (event instanceof HttpResponse) {
          if (event.status === 200) {             
            const fileName = event.body.files[0];
            const originalFileName = files[0].name;
            this.addToAttachment(fileName, fileType, originalFileName, files);           
            
          }
        }
      },
      (err)=> {
        this.isLoading = false;
        console.log(err);
        
      },
      () => {
        this.isLoading = false;
      }
      );
  }

  addToAttachment(fileName: string, fileType: string, originalFileName: string, file: any) {    
    let fileUrl = "/opt/rms/appendix/" + fileName;
    if (fileType === "01") {
      this.attachment_01[0].originalFileName = originalFileName;
      this.attachment_01[0].fileName = fileName;
      this.attachment_01[0].fileUrl = fileUrl;
      this.uploadedFile1 = file[0];
    } else if (fileType === "02") {
      this.attachment_02[0].originalFileName = originalFileName;
      this.attachment_02[0].fileName = fileName;
      this.attachment_02[0].fileUrl = fileUrl;
      this.uploadedFile2 = file[0];
    } else if (fileType === "03") {
      this.attachment_03[0].originalFileName = originalFileName;
      this.attachment_03[0].fileName = fileName;
      this.attachment_03[0].fileUrl = fileUrl;
      this.uploadedFile3 = file[0];
    } else if (fileType === "04") {
      this.attachment_04[0].originalFileName = originalFileName;
      this.attachment_04[0].fileName = fileName;
      this.attachment_04[0].fileUrl = fileUrl;
      this.uploadedFile4 = file[0];
    } else if (fileType === "05") {
      this.attachment_05[0].originalFileName = originalFileName;
      this.attachment_05[0].fileName = fileName;
      this.attachment_05[0].fileUrl = fileUrl;
      this.uploadedFile5 = file[0];
    } else if (fileType === "06") {
      this.attachment_06[0].originalFileName = originalFileName;
      this.attachment_06[0].fileName = fileName;
      this.attachment_06[0].fileUrl = fileUrl;
      this.uploadedFile6 = file[0];
    } else if (fileType === "07") {
      this.attachment_07[0].originalFileName = originalFileName;
      this.attachment_07[0].fileName = fileName;
      this.attachment_07[0].fileUrl = fileUrl;
      this.uploadedFile7 = file[0];
    }
  }

  attachmentSetter() {}

  cancelUploadedFiles(fileName: string){    
    if (fileName == '01') {
      let documentFileName = this.attachment_01[0].fileName;      
      this.deleteFile(documentFileName, "/opt/rms/appendix/", fileName);
    } else if (fileName === "02") {
      let documentFileName = this.attachment_02[0].fileName;      
      this.deleteFile(documentFileName, "/opt/rms/appendix/", fileName);
    } else if (fileName === "03") {
      let documentFileName = this.attachment_03[0].fileName;      
      this.deleteFile(documentFileName, "/opt/rms/appendix/", fileName);
    } else if (fileName === "04") {
      let documentFileName = this.attachment_04[0].fileName;      
      this.deleteFile(documentFileName, "/opt/rms/appendix/", fileName);
    } else if (fileName === "05") {
      let documentFileName = this.attachment_05[0].fileName;      
      this.deleteFile(documentFileName, "/opt/rms/appendix/", fileName);
    } else if (fileName === "06") {
      let documentFileName = this.attachment_06[0].fileName;      
      this.deleteFile(documentFileName, "/opt/rms/appendix/", fileName);
    } else if (fileName === "07") {
      let documentFileName = this.attachment_07[0].fileName;      
      this.deleteFile(documentFileName, "/opt/rms/appendix/", fileName);
    }
  }

  deleteFile(name: string, fileDir: string, fileName: string){  
    this.isLoading = true;  
    const url = `${resourceServerUrl}/v1/file/delete/${name}`;
    this.fileService.deleteUplodedFile(url, fileDir).subscribe(
      res => {
        console.log(res); 
      },
      (err)=>{ 
        this.isLoading = false;
        console.log(err);
      },
      ()=> {
        if(fileName == '01'){
      this.attachment_01[0].originalFileName = "";
      this.attachment_01[0].fileName = "";      
      this.attachment_01[0].fileUrl = "";
      this.stepOneBOEFileProgress1 =0;
        }else if(fileName == '02'){
          this.attachment_02[0].originalFileName = '';
          this.attachment_02[0].fileName = "";
          this.attachment_02[0].fileUrl = "";
          this.stepOneBOEFileProgress2 =0;
        }else if(fileName == '03'){
          this.attachment_03[0].originalFileName = '';
          this.attachment_03[0].fileName = "";
          this.attachment_03[0].fileUrl = "";
          this.stepOneBOEFileProgress3 =0;
        }else if(fileName == '04'){
          this.attachment_04[0].originalFileName = '';
          this.attachment_04[0].fileName = "";
          this.attachment_04[0].fileUrl = "";
          this.stepOneBOEFileProgress4 =0;
        }else if(fileName == '05'){
          this.attachment_05[0].originalFileName = '';
          this.attachment_05[0].fileName = '';
          this.attachment_05[0].fileUrl = '';
          this.stepOneBOEFileProgress5 =0;
        }else if(fileName == '06'){
          this.attachment_06[0].originalFileName = '';
          this.attachment_06[0].fileName = '';
          this.attachment_06[0].fileUrl = '';
          this.stepOneBOEFileProgress6 =0;
        }else if(fileName == '07'){
          this.attachment_07[0].originalFileName = '';
          this.attachment_07[0].fileName = '';
          this.attachment_07[0].fileUrl = '';
          this.stepOneBOEFileProgress7 =0;
        }
        this.isLoading = false;
      }
    );
}
public height2: number = 0;

public autosize(){
 
    var text = $('.autosize');
    this.height2 = text[0].scrollHeight;
    console.log(this.height2);    
    // text.each(function(){
    //     $(this).attr('rows',1);
    //     resize($(this));
    // });

    // text.on('input', function(){
    //     resize($(this));
    // });
    
    // function resize ($text) {
        
    //     $text.css('height', 'auto');
    //     $text.css('height', $text[0].scrollHeight+'px');
       
    // }    
}

 private downloadForDraftPreview(fileName: string, documentName: string){  
    this.nbrApplicationService.getStepTwoFileDownload(fileName).subscribe(
      (res) => {          
        if (res.status === 200) {   
            if(documentName.match('পরিশিষ্ট - \"ক\" অনুযায়ী পুরস্কার মঞ্জুরীর জন্য তথ্য')){
              this.uploadedFile1 = res.body;              
            }else if(documentName.match('অনিয়ম বা শুল্ক ফাঁকি সংক্রান্ত মামলার বিবরণী')){
              this.uploadedFile2 = res.body;
            }else if(documentName.match('শুল্ক ফাঁকি মামলার ন্যায় নির্ণয়ের আদেশের কপি')){
              this.uploadedFile3 = res.body;
            }else if(documentName.match('পুরস্কার প্রাপ্তির আবেদনের বিষয়টি আদালতে বিচারাধীন নয় মর্মে অফিস প্রধানের প্রত্যায়নপত্র')){
              this.uploadedFile4 = res.body;
            }else if(documentName.match('পুরস্কার প্রাপ্তির আবেদনের বিষয়ে ইতোপূর্বে পুরস্কার গ্রহণ করা হয়নি মর্মে অফিস প্রধানের প্রত্যায়নপত্র')){
              this.uploadedFile5 = res.body;
            }else if(documentName.match('ঘোষণা অনুযায়ী প্রদেয় রাজস্স সংক্রান্ত তথ্য')){
              this.uploadedFile6 = res.body;
            }else if(documentName == 'আদায়কৃত শুল্ক-কর ও জরিমানা সরকারি কোষাগারে জমার প্রমাণপত্র (জমার সঠিকতা যাচাইকৃত)'){
              this.uploadedFile7 = res.body;                            
            }
        }
      },
      (err) => {
        this.isLoading = false;
        console.log(err);
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
  

