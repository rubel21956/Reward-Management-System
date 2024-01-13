import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ApplicationCustomsStepOne } from '@app/auth/application/model/applicationCustomsStepOne';
import { ApplicationService } from '@app/auth/application/services/application.service';
import { MenuItem, MessageService } from 'primeng/api';
import { ActivatedRoute, Router } from '@angular/router';
import { OfficeService } from '@app/auth/office/services/office.service';
import { SharedService } from '@app/common/services/shared.service';
import { DomSanitizer } from '@angular/platform-browser';
import { ApplicationCustomsStepTwo } from '../model/applicationCustomsStepTwo';
import { saveAs } from 'file-saver';

@Component({
    selector: 'app-application-customs-step-four',
    templateUrl: './application-customs-step-four.component.html',
    styleUrls: ['./application-customs-step-four.component.css']
})
export class ApplicationCustomsStepFourComponent implements OnInit {

    oid!: string;
    public isLoading: boolean = false;
    public myTxtArea: string;
    temp: ApplicationCustomsStepTwo
    temp1: ApplicationCustomsStepOne
    applicationCustomsStepFourGroup: ApplicationCustomsStepOne;
    applicationCustomsStepTwoGroup: ApplicationCustomsStepTwo;
    applicationAttachments: any[];
    public rmsRole = '';
    pdfData: any;

    public pageStatus: any;

    items: MenuItem[];
    activeIndex: number = 2;

    constructor(private applicationCustomsStepTwo: FormBuilder, private applicationService: ApplicationService,
        private messageService: MessageService, private router: Router, private officeService: OfficeService,
        private sharedService: SharedService, private activateRoute: ActivatedRoute,
        private sanitizer: DomSanitizer) {
    }

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

        window.scrollTo(0,0);
        this.oid = this.activateRoute.snapshot.params['oid'];
        this.getApplicationCustomsStepOne(this.activateRoute.snapshot.params['oid']);
        this.getApplicationCustomsStepTwo(this.activateRoute.snapshot.params['oid']);
        }else{

            this.router.navigate(['page-not-found']);
        }
    }

    prevToApplicationCustomsStepTwo() {
        this.router.navigate(['../application-customs-step-two',
            this.activateRoute.snapshot.params['oid']], { relativeTo: this.activateRoute })
    }

    submitApplicationCustomsStepFour() {
        this.isLoading = true;
        if (this.applicationCustomsStepTwoGroup.applicationStatus === 'Submitted' ||
            this.applicationCustomsStepTwoGroup.applicationStatus === 'Rejected') {
            this.router.navigate([`application-customs-step-five/${this.activateRoute.snapshot.params['oid']}`])
        } else {
            this.router.navigate([`application-customs-step-five/${this.oid}`])
        }
    }

    // Modified By Arif
    // Reason: To view pdf into printable screen.
    printPdf() {
        const pdfUrl = URL.createObjectURL(this.pdfData);
        const popupWin = window.open(pdfUrl, '_blank', 'fullscreen=yes');
        this.isLoading = false;
        // setTimeout(() => {
        //   popupWin?.print();
        // }, 1000);
      }

    printPreviewApplicationCustomsStepFour() {
        this.isLoading = true;
        this.applicationService.applicationStepFourReport(this.activateRoute.snapshot.paramMap.get('oid')).subscribe(
            res => {
            if (res.status === 200) {
                this.pdfData = res.body;
                this.printPdf();
                //saveAs(res.body);
            }
        },
            err => {
                this.isLoading = false;
                if (err.error && err.error.message) {
                    this.messageService.add({ severity: 'error', summary: 'Data not found', detail: '' });
                }
            },
            () => {
                this.isLoading = false;
            });
    

        return false;
    }

    forwardToApplicationCustomsStepFive() {
        this.router.navigate([`application-customs-step-five/${this.activateRoute.snapshot.paramMap.get('oid')}`])
    }

    getApplicationCustomsStepOne(oid: string) {
        this.isLoading = true;
        this.applicationService.getApplicationCustomsStepOneOid(oid).subscribe(res => {
            if (res.status === 200) {
                this.applicationCustomsStepFourGroup = res.body;
                this.temp1 = res.body.applicationCustomsStepOneDto;
                this.temp = res.body.applicationCustomsStepTwoDto;
                this.removeTextAreaWhiteSpace();
            }
        },
            err => {
                this.isLoading = false;
                if (err.error && err.error.message) {
                    this.messageService.add({ severity: 'error', summary: err.error.message, detail: '' });
                }
            }, ()=>{
                this.isLoading = false; 
                this.activateRoute.queryParams.subscribe(params => {
                    // this.receivedId = +params['id'];                    
                    document.getElementById("textar").style.height = 'auto';
                    document.getElementById("textar").style.height = params['height']+'px';
                    if(params['height'] == '0'){
                        document.getElementById("textar").style.height = 'auto';
                        document.getElementById("textar").style.height = '500px'
                    }                    
                  });

                 
            });
    }
   

    removeTextAreaWhiteSpace() {
        this.myTxtArea = this.temp.applicationNarration;
        this.myTxtArea = this.myTxtArea.replace(/^\s*|\s*$/g, '');
    }

    getApplicationCustomsStepTwo(oid: string) {
        this.isLoading = true;
        this.applicationService.getApplicationCustomsStepTwoOid(oid).subscribe(res => {
            if (res.status === 200) {
                this.applicationCustomsStepTwoGroup = res.body;
                this.applicationAttachments = this.applicationCustomsStepTwoGroup.applicationAttachments
            }
        },
            err => {
                this.isLoading = false;
                if (err.error && err.error.message) {
                    this.messageService.add({ severity: 'error', summary: err.error.message, detail: '' });
                }
            },
            ()=>{
                var tx = document.getElementById("textar") as HTMLTextAreaElement; 
                tx.style.height = tx.scrollHeight+'px';                   
               
            }
            );
    }
}



