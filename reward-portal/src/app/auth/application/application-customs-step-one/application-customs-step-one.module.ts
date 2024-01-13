import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ApplicationCustomsStepOneRoutingModule } from './application-customs-step-one-routing.module';
import { ApplicationCustomsStepOneComponent } from './application-customs-step-one.component';
import { PrimengModule } from '@app/common/modules/primeng-modules';
import { TranslateModule } from '@ngx-translate/core';
import { LoadingImageModule } from '@app/common/components/loading-image/loading-image.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { HttpClientModule } from '@angular/common/http';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { HotTableModule } from '@handsontable/angular';
import { ProgressBarModule } from 'primeng/progressbar';
import { StepsModule } from 'primeng/steps';


@NgModule({
  declarations: [
    ApplicationCustomsStepOneComponent
  ],
  imports: [
    CommonModule,
    ApplicationCustomsStepOneRoutingModule,
    PrimengModule,
    TranslateModule,
    LoadingImageModule,
    ReactiveFormsModule,
    DropdownModule,
    FileUploadModule,
    HttpClientModule,
    InputTextModule,
    InputNumberModule,
    FormsModule,
    HotTableModule.forRoot(),
    ProgressBarModule,
    StepsModule
  ]
})
export class ApplicationCustomsStepOneModule { }
