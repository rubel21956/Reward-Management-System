import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import { SharedService } from '@app/common/services/shared.service';
import {MessageService} from 'primeng/api';
import {Office} from '../../model/office';
import {OfficeService} from '../../services/office.service';

@Component({
    selector: 'app-update-office',
    templateUrl: './update-office.component.html',
    styleUrls: ['./update-office.component.css']
})
export class UpdateOfficeComponent implements OnInit {

    public isLoading: boolean = false;
    officeGroup: FormGroup;
    office: Office;
    selectedCountry: any;
    selectedItem: any;
    offices: any[];
    officeList: Office[];
    uploadedFiles: any[] = [];
    statusList: any[];
    public rmsRole = ''; 

    constructor(private fb: FormBuilder, private officeService: OfficeService, private messageService: MessageService,
                private activateRoute: ActivatedRoute, private router: Router, private sharedService: SharedService) {

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
        // this.getOffices();

        this.officeGroup = this.fb.group({
            code: ['', [Validators.required]],
            name: ['', [Validators.required]],
            address1: ['', [Validators.required]],
            address2: ['', [Validators.required]],
            status: ['', [Validators.required]],
            approverOffice: [''],
            itOffice: [''],
            officeAdminOffice: ['']
        });

        this.getOfficeList();
        this.getOfficeByOid(this.activateRoute.snapshot.paramMap.get('oid'));
        }
    }

    getOfficeByOid(id: string) {
        this.isLoading = true;
        this.officeService.getByOid(id).subscribe(res => {
                if (res.status === 200) {
                    this.office = res.body;
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
    }

    setFormValue() {
        this.officeGroup.patchValue({
            code: this.office.code,
            name: this.office.name,
            address1: this.office.address1,
            address2: this.office.address2,
            status: this.office.status,
            approverOffice: this.office.approverOffice,
            itOffice: this.office.itOffice,
            officeAdminOffice: this.office.officeAdminOffice
        });
    }

    onCancel() {
        this.router.navigate(['manage-office'])
    }

    onSubmit() {
        this.isLoading = true;
        if (this.officeGroup.valid) {
            this.officeService.updateInfo(this.officeGroup.value, this.activateRoute.snapshot.paramMap.get('oid')).subscribe(res => {
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

    onUpload(event) {
        for (let file of event.files) {
            this.uploadedFiles.push(file);
        }
        this.messageService.add({
            severity: 'info',
            summary: 'File Uploaded',
            detail: ''
        });
    }

    getOfficeList() {

    }

}
