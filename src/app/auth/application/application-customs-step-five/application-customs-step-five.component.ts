import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {resourceServerUrl} from '@app/common/constants/server-settings';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import {FileService} from '@app/common/services/file.service';
import {MenuItem, MessageService} from 'primeng/api';
import {ApplicationService} from '../services/application.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ApplicationCustomsStepTwo} from '@app/auth/application/model/applicationCustomsStepTwo';
import {ApplicationCustomsStepFive} from '@app/auth/application/model/applicationCustomsStepFive';
import {ApplicationCustomsStepOne} from '@app/auth/application/model/applicationCustomsStepOne';
import { SharedService } from '@app/common/services/shared.service';
import { NbrApplicationService } from '@app/auth/nbr-admin/services/nbr-application.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import GcPdfViewer from '@grapecity/gcpdfviewer';

@Component({
    selector: 'app-application-customs-step-five',
    templateUrl: './application-customs-step-five.component.html',
    styleUrls: ['./application-customs-step-five.component.css']
})
export class ApplicationCustomsStepFiveComponent implements OnInit {

    oid: string;
    public isLoading: boolean = false;
    attachment: any = [];
    public uploadedFiles: any;
    public sessionList: any[];
    applicationCustomsStepFiveGroup: FormGroup;
    temp: ApplicationCustomsStepFive;
    temp1: ApplicationCustomsStepOne;
    temp2: ApplicationCustomsStepTwo;
    public rmsRole = '';

    public downloadedFiveFilePath: SafeUrl='';

    public pageStatus: any;

    items: MenuItem[];
    activeIndex: number = 3;

    constructor(private fileService: FileService, private messageService: MessageService,
                private nbrApplicationService: NbrApplicationService, private sanitizer: DomSanitizer,
                private applicationService: ApplicationService, private router: Router, private sharedService: SharedService,
                private applicationCustomsStepFive: FormBuilder, private activateRoute: ActivatedRoute) {
    }

    // ngAfterViewInit() {
    //     const viewer = new GcPdfViewer("#viewer", {
    //       workerSrc: "//node_modules/@grapecity/gcpdfviewer/gcpdfviewer.worker.js",
    //       restoreViewStateOnLoad: false
    //     });
    //     viewer.addDefaultPanels();

    //     viewer.addDefaultPanels();
    //     viewer.open(this.downloadedFiveFilePath);
    //   }

    ngOnInit(): void {
        this.rmsRole = this.sharedService.getRmsRole();
        if(this.rmsRole == 'role-operator' || this.rmsRole == "role-nbr-investigation"){
           
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

        this.applicationCustomsStepFiveGroup = this.applicationCustomsStepFive.group({
            oid: this.oid,
            applicationAttachmentEntity: {}
        });
        if (this.activateRoute.snapshot.params['oid']) {
            this.getApplicationCustomsStepFiveDetailsByOid();            
        }
        this.oid = this.activateRoute.snapshot.params['oid'];
        }else{
            this.router.navigate(['page-not-found']);
    }
    };

    getApplicationCustomsStepFiveDetailsByOid() {
        this.isLoading = true;
   
        this.applicationService.getApplicationCustomsOid(this.activateRoute.snapshot.params['oid']).subscribe(res => {
                if (res.status === 200) {
                    this.sessionList = res.body;
                    this.temp = res.body.applicationCustomsStepFiveDto;
                    this.attachment = res.body.applicationCustomsStepFiveDto.applicationAttachmentEntity; 
                    if(this.attachment != null){
                        this.uploadedFiles = this.attachment.originalFileName;  
                    }else{
                        this.uploadedFiles = '';
                    }                                                                                 
                    this.temp1 = res.body.applicationCustomsStepOneDto;
                    this.temp2 = res.body.applicationCustomsStepTwoDto;
                    // this.setFormValue();
                }
            },
            err => {
                this.isLoading = false;
                if (err.error && err.error.message) {
                    this.messageService.add({severity: 'error', summary: err.error.message, detail: ''});
                }
            },
            () => {
                this.isLoading = false;
                this.getStepFiveFile();
            });
    }

    // setFormValue() {
    //     this.applicationCustomsStepFiveGroup.patchValue({
    //         applicationAttachmentEntity: this.temp.applicationAttachments[0].fileName
    //     });
    // }


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
        this.uploadedFiles = files[0].name;
        
        // this.downloadedFiveFilePath = files[0]
        const fileExt = files[0].name.split('.').pop();
        if (!['pdf', 'PDF', "jpg", "jpeg", "JPEG", "JPG", "png", "PNG"].includes(fileExt)) {
            this.messageService.add({severity: 'error', summary: 'Please select only pdf or jpg/png', detail: ''});
            return;
        }
        if (!files) {
            return;
        }
        this.pdfData = files[0];
        const url = `${resourceServerUrl}/v1/file/upload`;
        const formData = new FormData();
        formData.append('files', files[0]);
        this.fileService.upload(files, url, formData, 'submitted-application').subscribe((event: any) => {
            if (event.type === HttpEventType.UploadProgress) {
                for (let i = 0; i < files.length; i++) {
                    //   this.files[i].progress = Math.round(100 * event.loaded / event.total);
                    this.uploadedProgress = Math.round(100 * event.loaded / event.total);
                }
            } else if (event instanceof HttpResponse) {
                if (event.status === 200) {
                    const fileName = event.body.files[0];
                    // this.getUserFile1(fileName);
                    let fileUrl = '/opt/rms/submitted-application/' + fileName
                    // this.downloadedFiveFilePath = fileUrl
                    this.downloadedFiveFilePath = fileUrl;
                    
                    if (fileType === 'submitted-application') {
                        this.attachment = [
                            {
                                oid: this.oid,
                                fileName: fileName,
                                attachmentType: 'সাক্ষরিত আবেদনপত্র',
                                fileUrl: fileUrl,
                                originalFileName: this.uploadedFiles
                            }
                        ]
                    }
                }
            }
        })
    }

    getUserFile1(refId: string) {
        this.isLoading = true;
        this.nbrApplicationService.getStepFiveFileDownload(refId).subscribe(file => {
                // this.downloadedFilePath = '';
                const unsafeImageUrl = URL.createObjectURL(file);
                this.downloadedFiveFilePath = this.sanitizer.bypassSecurityTrustUrl(unsafeImageUrl);                
            },
            err => {
                this.isLoading = false;
                if (err.error && err.error.message) {
                    this.messageService.add({severity: 'error', summary: err.error.message, detail: ''});
                }
            });
    }

    private getStepFiveFile() { 
        this.isLoading = true;    
        if(this.attachment != null){
            let serviceParam = this.attachment.fileName; 
            this.nbrApplicationService.getStepFiveFileDownload(serviceParam).subscribe(
                (res) => {                        
                  if (res.status === 200) {                       
                  this.pdfData = res.body;              
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
              )
        }   
                                       
        
      }

    //////////////////////////////////// Modified by Arif \\\\\\\\\\\\\\\\\\\\\\\\\\\
    // Reason: Added next & download Button 
    private pdfData: any;
    public confirmModal: boolean = false;
    public uploadedProgress: number = 0;

    printPdf() {
        const pdfUrl = URL.createObjectURL(this.pdfData);
        const popupWin = window.open(pdfUrl, '_blank', 'fullscreen=yes');
        // setTimeout(() => {
        //   popupWin?.print();
        // }, 1000);
      }

      backToStepThree(){
        this.router.navigate(['../application-customs-step-four',
        this.activateRoute.snapshot.params['oid']])
      }

    prevToApplicationCustomsStepThree() {
        if(this.uploadedFiles){
            this.confirmModal = true;
        }else{
           this.backToStepThree();
        }
        
    }

    printPreviewApplicationCustomsStepFour() {
        this.applicationService.applicationStepFourReport(this.activateRoute.snapshot.paramMap.get('oid')).subscribe(res => {
            if (res.status === 200) {
                this.pdfData = res.body;
                this.printPdf();
                // saveAs(res.body);
            }
        },
            err => {
                if (err.error && err.error.message) {
                    this.messageService.add({ severity: 'error', summary: 'Data not found', detail: '' });
                }
            },
            () => {
                
            });
        setTimeout(() => {
        }, 3000);

        return false;
    }


    submitApplicationCustomsStepFive() {
        this.isLoading = true;
        this.applicationCustomsStepFiveGroup.value.oid = this.oid;                        
        this.applicationCustomsStepFiveGroup.get("applicationAttachmentEntity").setValue(this.attachment[0]) ;
                
        if (this.applicationCustomsStepFiveGroup.valid) {
                                                                  
            this.applicationService.saveApplicationCustomsStepFiveUsingPUT(this.applicationCustomsStepFiveGroup.value, this.oid).subscribe(res => {                
                    if (res.status === 200) {                                             
                        this.isLoading = true;
                        this.messageService.add({
                            severity: 'success',
                            summary: 'আবেদনটি সফলভাবে এনবিআর এর সংশ্লিষ্ট কর্তৃপক্ষ এর কাছে প্রেরিত হয়েছে',
                            detail: ''
                        });
                        setTimeout(() => {
                            this.router.navigate(['application-customs-list'])
                        }, 2000);
                    }
                },
                err => {
                    this.isLoading = false;
                    if (err.error && err.error.message) {
                        this.messageService.add({severity: 'error', summary: 'নতুন স্বাক্ষরিত আবেদনপত্র সংযুক্ত করুন।', detail: ''});
                    }
                });
        } else {
            this.isLoading = false;
            this.messageService.add({severity: 'error', summary: 'অনুগ্রহ পূর্বক প্রয়োজনীয় তথ্য প্রদান করুন', detail: ''});
        }
    }

    cancleUploadedFile(){   
        this.isLoading = true;   
        this.deleteFile(this.attachment.fileName, "/opt/rms/submitted-application/");
    }

    deleteFile(name: string, fileDir: string){
        const url = `${resourceServerUrl}/v1/file/delete/${name}`;
        this.fileService.deleteUplodedFile(url, fileDir).subscribe(
          res => {},
          (err)=>{
            this.isLoading = false;
          },
          ()=> {
            this.isLoading = false;
            this.uploadedFiles = '';
            this.uploadedProgress = 0;        
            this.attachment = [];
          }
        );
    }

    confirmStay() {
        // this.displayModal = false;
        // this.confirmModal = false;
        this.confirmModal = false;
      }


}
