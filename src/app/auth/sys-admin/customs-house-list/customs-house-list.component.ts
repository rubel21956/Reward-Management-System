import {Component, OnInit} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {DropdownModule} from 'primeng/dropdown';
import {ActivatedRoute, Router} from '@angular/router';
import {SysadminService} from '@app/auth/sys-admin/services/sysadmin.service';
import { SharedService } from '@app/common/services/shared.service';

@Component({
    selector: 'app-customs-house-list',
    templateUrl: './customs-house-list.component.html',
    styleUrls: ['./customs-house-list.component.css']
})
export class CustomsHouseListComponent implements OnInit {

    public isLoading: boolean = false;
    public sessionList: any[];
    public rmsRole = '';

    constructor(private router: Router, private activateRoute: ActivatedRoute,
                private sysadminService: SysadminService, private sharedService: SharedService) {
    }

    ngOnInit(): void {
        this.rmsRole = this.sharedService.getRmsRole();
        if(this.rmsRole !== 'role-sys-admin'){
            this.router.navigate(['page-not-found']);
        }else{
        this.getCustomsHouseList();
        }
    }

    createCustomsHouse() {
        this.router.navigate(['customs-house-details'], {relativeTo: this.activateRoute})
    }

    getCustomsHouseList(): void {
        this.sysadminService.getCustomsHouseList().subscribe(res => {
                if (res.status === 200) {
                    this.sessionList = res.body
                }
            },
            err => {
                if (err.status === 404) {
                    this.sessionList = [];
                }
                if (err.error && err.error.message) {
                }
            },
            () => {
            });
    }

    editCustomsHouse(i: any) {
        this.router.navigate(['customs-house-details',
            this.sessionList[i].oid], {relativeTo: this.activateRoute})
    }
}
