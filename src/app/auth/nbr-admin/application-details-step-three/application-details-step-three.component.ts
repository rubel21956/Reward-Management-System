import {Component, OnInit} from '@angular/core';
import {FormGroup} from '@angular/forms';

@Component({
    selector: 'app-application-details-step-three',
    templateUrl: './application-details-step-three.component.html',
    styleUrls: ['./application-details-step-three.component.css']
})
export class ApplicationDetailsStepThreeComponent implements OnInit {

    applicationDetailsStepThreeGroup: FormGroup

    constructor() {
    }

    ngOnInit(): void {
    }
}
