import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {NbrApplicationService} from '@app/auth/nbr-admin/services/nbr-application.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
    selector: 'app-application-details-step-one',
    templateUrl: './application-details-step-one.component.html',
    styleUrls: ['./application-details-step-one.component.css']
})
export class ApplicationDetailsStepOneComponent implements OnInit {

    index: number = 0;
    public sessionList: any[];
    public isLoading: boolean = false;
    applicationDetailsStepOneGroup: FormGroup;
    products: any[];

    constructor(private applicationList: FormBuilder, private nbrApplicationService: NbrApplicationService,
                private router: Router, private activateRoute: ActivatedRoute) {
    }

    ngOnInit(): void {
    }

    nextPage() {
        this.router.navigate(['../../nbr-admin-step-two/8767657576'], {relativeTo: this.activateRoute})
    }
}
