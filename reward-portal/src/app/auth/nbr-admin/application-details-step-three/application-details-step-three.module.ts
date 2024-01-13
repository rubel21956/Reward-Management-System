import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ApplicationDetailsStepThreeRoutingModule } from './application-details-step-three-routing.module';
import { ApplicationDetailsStepThreeComponent } from './application-details-step-three.component';
import {ReactiveFormsModule} from '@angular/forms';
import {ToastModule} from 'primeng/toast';


@NgModule({
  declarations: [
    ApplicationDetailsStepThreeComponent
  ],
    imports: [
        CommonModule,
        ApplicationDetailsStepThreeRoutingModule,
        ReactiveFormsModule,
        ToastModule
    ]
})
export class ApplicationDetailsStepThreeModule { }
