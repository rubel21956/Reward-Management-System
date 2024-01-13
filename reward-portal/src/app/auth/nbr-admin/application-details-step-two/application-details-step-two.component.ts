import {Component, OnInit} from '@angular/core';
import {DropdownModule} from 'primeng/dropdown';
import {FormBuilder, FormGroup} from '@angular/forms';
import {NbrApplicationService} from '@app/auth/nbr-admin/services/nbr-application.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
    selector: 'app-application-details-step-two',
    templateUrl: './application-details-step-two.component.html',
    styleUrls: ['./application-details-step-two.component.css']
})
export class ApplicationDetailsStepTwoComponent implements OnInit {

    products: any[];
    tempList: DropdownModule;
    public isLoading: boolean = false;
    applicationDetailsStepTwoGroup: FormGroup;

    constructor(private applicationList: FormBuilder, private nbrApplicationService: NbrApplicationService,
                private router: Router, private activateRoute: ActivatedRoute) {
        this.tempList = [
            {name: 'আবেদন বিবেচনাযোগ্য'},
            {name: 'আবেদন বিবেচনাযোগ্য নয়'},
            {name: 'বিবেচনার জন্য প্রয়োজনীয় নথি প্রয়োজন'}
        ];
    }

    get f() {
        return this.applicationDetailsStepTwoGroup.controls;
    }

    ngOnInit(): void {
    }

    nextPage() {
        this.router.navigate(['nbr-admin-step-three', 'oid'], {relativeTo: this.activateRoute})
    }

    printPreview() {
        window.print();
    }
}
