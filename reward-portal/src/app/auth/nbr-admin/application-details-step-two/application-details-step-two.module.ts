import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ApplicationDetailsStepTwoRoutingModule } from './application-details-step-two-routing.module';
import { ApplicationDetailsStepTwoComponent } from './application-details-step-two.component';
import {DropdownModule} from 'primeng/dropdown';
import {TableModule} from 'primeng/table';
import { PrimengModule } from '@app/common/modules/primeng-modules';
import { TranslateModule } from '@ngx-translate/core';


@NgModule({
  declarations: [
    ApplicationDetailsStepTwoComponent
  ],
  imports: [
    CommonModule,
    ApplicationDetailsStepTwoRoutingModule,
    DropdownModule,
    TableModule,
    PrimengModule,
    TranslateModule,
  ]
})
export class ApplicationDetailsStepTwoModule { }
