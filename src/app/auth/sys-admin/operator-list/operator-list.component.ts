import {Component, OnInit} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {SysadminService} from '@app/auth/sys-admin/services/sysadmin.service';
import { SharedService } from '@app/common/services/shared.service';

@Component({
    selector: 'app-operator-list',
    templateUrl: './operator-list.component.html',
    styleUrls: ['./operator-list.component.css']
})
export class OperatorListComponent implements OnInit {

    public isLoading: boolean = false;
    public sessionList: any[];
    public rmsRole = '';

    constructor(private router: Router, private activateRoute: ActivatedRoute,
                private sysadminService: SysadminService,
                private sharedService: SharedService) {
    }

    ngOnInit(): void {
        this.rmsRole = this.sharedService.getRmsRole();
        if(this.rmsRole !== 'role-sys-admin'){
            this.router.navigate(['page-not-found']);
        }else{
        this.getOperatorList();
        }
    }

    createOperator() {
        this.router.navigate(['operator-details'], {relativeTo: this.activateRoute})
    }

    getOperatorList(): void {
        this.sysadminService.getOperatorList().subscribe(res => {
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

    editOperatorDetails(i: any) {                
        this.router.navigate(['operator-details',
            this.sessionList[i].oid], {relativeTo: this.activateRoute})
    }
}
