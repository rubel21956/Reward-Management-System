import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {SysadminService} from '@app/auth/sys-admin/services/sysadmin.service';
import {MessageService} from 'primeng/api';
import {ApplicationService} from '@app/auth/application/services/application.service';
import {NID_REGEX} from '@app/common/constants/input-validation-rules';
import {OperatorRequestDTO} from '@app/auth/sys-admin/model/operatorRequestDTO';
import { SharedService } from '@app/common/services/shared.service';
import { PrimeNGConfig } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';




@Component({
    selector: 'app-operator-details',
    templateUrl: './operator-details.component.html',
    styleUrls: ['./operator-details.component.css']
})
export class OperatorDetailsComponent implements OnInit {

    operatorDetailsGroup: FormGroup
    minDate: Date;
    maxDate: Date;
    public isLoading: boolean = false;
    submitted: boolean = false;
    public oid?: string;
    public statusList: any = [];
    uidCheck: any;
    public sessionList: any[];
    public temp: OperatorRequestDTO;
    public rmsRole = '';
    public userIdCheck = false;
    public userPassword: string = "";
    

    applyingOffice: any = [];

    constructor(private operatorRequestDTO: FormBuilder, private router: Router, private sharedService: SharedService,
                private activateRoute: ActivatedRoute, private applicationService: ApplicationService,
                private sysadminService: SysadminService, private messageService: MessageService,
                private config: PrimeNGConfig,
                private translateService: TranslateService) {

        const today = new Date();
        this.minDate = new Date('January 01, 1900');
        this.maxDate = new Date(today);

        this.statusList = [
            {bnname: 'সক্রিয়', value: 'সক্রিয়'},
            {bnname: 'নিষ্ক্রিয়', value: 'নিষ্ক্রিয়'}
        ];
    }

    get f() {
        return this.operatorDetailsGroup.controls;
    }

    ngOnInit(): void {
        this.rmsRole = this.sharedService.getRmsRole();
        if(this.rmsRole !== 'role-sys-admin'){
            this.router.navigate(['page-not-found']);
        }else{
        this.operatorDetailsGroup = this.operatorRequestDTO.group({
                oid: [''],
                name: ['', Validators.required],
                officeCode: ['', Validators.required],
                dob: ['', Validators.required],
                nidNumber: ['',
                    Validators.compose([
                        Validators.required,
                        Validators.pattern(NID_REGEX),
                    ]),
                ],
                joiningDate: ['', Validators.required],
                userId: ['', Validators.required],
                contactNumber: ['', Validators.required],
                status: ['', Validators.required],
                designation: ['', Validators.required],
                email: ['', Validators.required],
                password: ['', Validators.required]
                
            }
            );
            this.getApplyingOfficeList();
            this.oid = this.activateRoute.snapshot.paramMap.get('oid');                        
            if (this.activateRoute.snapshot.params['oid']) {
                this.getOperatorDetailsByOid();
            }
        }
        this.translate("bn");
        
    
    }

    translate(lang: string) {
        this.translateService.use(lang);
        this.translateService.get('primeng').subscribe(res => this.config.setTranslation(res));   
      }

    prevToOperatorList() {
        this.router.navigate(['operator-list'])
    }

    resetOperatorDetails() {
        this.operatorDetailsGroup = this.operatorRequestDTO.group({
                oid: [''],
                name: [''],
                officeCode: [''],
                dob: [''],
                nidNumber: [''],
                joiningDate: [''],
                userId: [''],
                contactNumber: [''],
                status: [''],
                email: [''],
                designation: [''],
                password: ['']
                
            }
        );
    }

    getApplyingOfficeList(): void {
        this.applicationService.getOfficeList().subscribe(res => {
                if (res.status === 200) {
                    this.applyingOffice = res.body
                }
            },
            err => {
                if (err.status === 404) {
                    this.applyingOffice = [];
                }
                if (err.error && err.error.message) {
                }
            },
            () => {
            });
    }

    getOperatorDetailsByOid() {
        this.isLoading = true;
        this.sysadminService.getOperatorByOid(this.activateRoute.snapshot.params['oid']).subscribe(res => {
                if (res.status === 200) {
                    this.sessionList = res.body;                                     
                    this.temp = res.body;
                    this.userPassword = this.temp.userId
                    this.setFormValue();
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
            });
            console.log(this.userPassword);
            
            this.sysadminService.getOperatorPassword("জামাল-০১").subscribe((data) => {
                console.log(data);
                
            })
    }

    setFormValue() {            
        this.operatorDetailsGroup = this.operatorRequestDTO.group({
                name: this.temp.name,
                officeCode: this.temp.officeCode,
                dob: this.temp.dob ? new Date(this.temp.dob) : null,
                nidNumber: this.temp.nidNumber,
                joiningDate: this.temp.joiningDate ? new Date(this.temp.joiningDate) : null,
                userId: this.temp.userId,
                contactNumber: this.temp.contactNumber,
                status: this.temp.status,  
                designation: this.temp.designation,
                email: this.temp.email,
                password: "**********"
            }
        );
        this.userIdCheck = true;
    }

    submitOperatorDetails() {
        this.isLoading = true;
        if (this.operatorDetailsGroup.valid) {                        
            this.sysadminService.saveOperator(this.operatorDetailsGroup.value).subscribe(res => {
                    if (res.status === 200) {
                        this.messageService.add({severity: 'success', summary: 'অপারেটর সফলভাবে সংরক্ষণ করা হয়েছে', detail: ''});
                        setTimeout(() => {
                            this.router.navigate(['operator-list'])
                        }, 2000);
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
                });
        } else {
            this.isLoading = false;
            this.messageService.add({severity: 'error', summary: 'অনুগ্রহ পূর্বক প্রয়োজনীয় তথ্য প্রদান করুন', detail: ''});
        }
    }

    checkUserId(){
        this.sysadminService.getUserIdList(this.operatorDetailsGroup.value.userId).subscribe( res=> {
            this.uidCheck = res.body;
        })
    }

    checkdata(input: InputEvent){
        console.log(input);
        
    }

    updateOperatorDetails() {
        this.operatorDetailsGroup.value.oid = this.activateRoute.snapshot.paramMap.get('oid');
        this.isLoading = true;
        if (this.operatorDetailsGroup.valid) {
            console.log(this.operatorDetailsGroup.value);
            
            this.sysadminService.updateOperator(this.operatorDetailsGroup.value).subscribe(res => {
                    if (res.status === 200) {
                        this.messageService.add({severity: 'success', summary: 'অপারেটর সফলভাবে হালনাগাদ করা হয়েছে', detail: ''});
                        setTimeout(() => {
                            this.router.navigate(['operator-list'])
                        }, 2000);
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
                });
        } else {
            this.isLoading = false;
            this.messageService.add({severity: 'error', summary: 'অনুগ্রহ পূর্বক প্রয়োজনীয় তথ্য প্রদান করুন', detail: ''});
        }


    }
}
