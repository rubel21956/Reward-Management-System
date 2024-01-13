import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ApplicationDetailsStepOneRoutingModule } from './application-details-step-one-routing.module';
import {ApplicationDetailsStepOneComponent} from '@app/auth/nbr-admin/application-details-step-one/application-details-step-one.component';
import {TableModule} from 'primeng/table';

@NgModule({
  declarations: [
      ApplicationDetailsStepOneComponent
  ],
  imports: [
    CommonModule,
    ApplicationDetailsStepOneRoutingModule,
    TableModule
  ]
})
export class ApplicationDetailsStepOneModule { }
