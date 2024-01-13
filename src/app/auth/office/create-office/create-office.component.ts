import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {MessageService} from 'primeng/api';
import {OfficeService} from '../services/office.service';
import {Office} from '@app/auth/office/model/office';
import { SharedService } from '@app/common/services/shared.service';

@Component({
    selector: 'app-create-office',
    templateUrl: './create-office.component.html',
    styleUrls: ['./create-office.component.css']
})
export class CreateOfficeComponent implements OnInit {

    public isLoading: boolean = false;
    personalGroup: FormGroup;
    statusList: any[];
    officeList: Office[];
    public rmsRole = '';

    constructor(private fb: FormBuilder, private officeService: OfficeService,
                private router: Router, private messageService: MessageService,
                private sharedService: SharedService) {
        this.statusList = [
            {name: 'Active', oid: 'Active'},
            {name: 'Inactive', oid: 'Inactive'},
        ];
    }

    ngOnInit(): void {
        this.rmsRole = this.sharedService.getRmsRole();
        if(this.rmsRole !== 'role-sys-admin'){
            this.router.navigate(['page-not-found']);
        }else{
        this.personalGroup = this.fb.group({
            code: ['', [Validators.required]],
            name: ['', [Validators.required]],
            address1: ['', [Validators.required]],
            address2: ['', [Validators.required]],
            status: ['', [Validators.required]],
            approverOffice: [''],
            itOffice: [''],
            officeAdminOffice: ['']
        })

        this.getOfficeList();
        }
    }

    onCancel() {
        this.router.navigate(['manage-office']);
    }

    onSubmit() {
        this.isLoading = true;
        if (this.personalGroup.valid) {
            this.officeService.saveInfo(this.personalGroup.value).subscribe(res => {
                    if (res.status === 200) {
                        this.messageService.add({severity: 'success', summary: 'Office updated Successfully', detail: ''});
                        setTimeout(() => {
                            this.router.navigate(['manage-office'])
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
            this.messageService.add({severity: 'error', summary: 'Please fill up all the required fields', detail: ''});
        }
    }

    getOfficeList() {
    }
}
