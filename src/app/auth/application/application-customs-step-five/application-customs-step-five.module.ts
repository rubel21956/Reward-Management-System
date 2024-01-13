import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ApplicationCustomsStepFiveRoutingModule } from './application-customs-step-five-routing.module';
import { ApplicationCustomsStepFiveComponent } from './application-customs-step-five.component';
import {PrimengModule} from '@app/common/modules/primeng-modules';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { LoadingImageModule } from '@app/common/components/loading-image/loading-image.module';
import { FileUploadModule } from 'primeng/fileupload';
import { HttpClientModule } from '@angular/common/http';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { StepsModule } from 'primeng/steps';
import { PdfJsViewerModule } from 'ng2-pdfjs-viewer';
import { PdfViewerModule } from 'ng2-pdf-viewer';


@NgModule({
  declarations: [
    ApplicationCustomsStepFiveComponent
  ],
    imports: [
        CommonModule,
        ApplicationCustomsStepFiveRoutingModule,
        PrimengModule,
        ReactiveFormsModule,
        LoadingImageModule,
        FileUploadModule,
        HttpClientModule,
        InputTextModule,
        InputNumberModule,
        FormsModule,
        StepsModule,
        PdfJsViewerModule,
        PdfViewerModule
    ]
})
export class ApplicationCustomsStepFiveModule { }
