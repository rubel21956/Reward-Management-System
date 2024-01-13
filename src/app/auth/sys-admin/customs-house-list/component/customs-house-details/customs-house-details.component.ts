import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {SysadminService} from '@app/auth/sys-admin/services/sysadmin.service';
import {MessageService} from 'primeng/api';
import {OfficeRequestDTO} from '@app/auth/sys-admin/model/officeRequestDTO';
import { SharedService } from '@app/common/services/shared.service';

@Component({
    selector: 'app-customs-house-details',
    templateUrl: './customs-house-details.component.html',
    styleUrls: ['./customs-house-details.component.css']
})
export class CustomsHouseDetailsComponent implements OnInit {

    customsHouseDetailsGroup: FormGroup;
    public isLoading: boolean = false;
    submitted: boolean = false;
    public oid?: string;
    public statusList: any = [];
    public sessionList: any[];
    public rmsRole = '';

    constructor(private officeRequestDTO: FormBuilder, private router: Router, private activateRoute: ActivatedRoute,
                private sysadminService: SysadminService, private messageService: MessageService,
                private sharedService: SharedService) {

        this.statusList = [
            {bnname: 'সক্রিয়', value: 'Active'},
            {bnname: 'নিষ্ক্রিয়', value: 'Inactive'}
        ];
    }

    get f() {
        return this.customsHouseDetailsGroup.controls;
    }

    ngOnInit(): void {
        this.rmsRole = this.sharedService.getRmsRole();
        if(this.rmsRole !== 'role-sys-admin'){
            this.router.navigate(['page-not-found']);
        }else{
        this.customsHouseDetailsGroup = this.officeRequestDTO.group({
            code: ['', Validators.required],
            bnname: ['', Validators.required],
            bnaddress2: ['', Validators.required],
            status: ['', Validators.required]
            }
        );
        this.oid = this.activateRoute.snapshot.paramMap.get('oid');
        if (this.activateRoute.snapshot.params['oid']) {
            this.getCustomsOfficeDetailsByOid();
        }
    }
    }

    prevToCustomsHouseList() {
        this.router.navigate(['customs-house-list'])
    }

    resetCustomsHouseDetails() {
        this.customsHouseDetailsGroup = this.officeRequestDTO.group({
                code: [''],
                bnname: [''],
                bnaddress2: [''],
                status: ['']
            }
        );
    }

    getCustomsOfficeDetailsByOid() {
        this.isLoading = true;
        this.sysadminService.getCustomsOfficeByOid(this.activateRoute.snapshot.params['oid']).subscribe(res => {
                if (res.status === 200) {
                    this.sessionList = res.body;
                    this.customsHouseDetailsGroup.patchValue(this.sessionList)
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
    }

    submitCustomsHouseDetails() {
        this.isLoading = true;
        if (this.customsHouseDetailsGroup.valid) {
            this.sysadminService.saveCustomsHouse(this.customsHouseDetailsGroup.value).subscribe(res => {
                    if (res.status === 200) {
                        this.messageService.add({severity: 'success', summary: 'কাস্টমস হাউস সফলভাবে সংরক্ষণ করা হয়েছে', detail: ''});
                        setTimeout(() => {
                            this.router.navigate(['customs-house-list'])
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

    updateCustomsHouseDetails() {
        this.isLoading = true;
        if (this.customsHouseDetailsGroup.valid) {
            this.sysadminService.updateCustomsHouse(this.customsHouseDetailsGroup.value,
                this.activateRoute.snapshot.paramMap.get('oid')).subscribe(res => {
                    if (res.status === 200) {
                        this.messageService.add({severity: 'success', summary: 'কাস্টমস হাউস সফলভাবে হালনাগাদ করা হয়েছে', detail: ''});
                        setTimeout(() => {
                            this.router.navigate(['customs-house-list'])
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
