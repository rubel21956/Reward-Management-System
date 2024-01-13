import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ApplicationCustomsStepTwoRoutingModule} from './application-customs-step-two-routing.module';
import {
    ApplicationCustomsStepTwoComponent
} from '@app/auth/application/application-customs-step-two/application-customs-step-two.component';
import {PrimengModule} from '@app/common/modules/primeng-modules';
import {TranslateModule} from '@ngx-translate/core';
import {LoadingImageModule} from '@app/common/components/loading-image/loading-image.module';
import {ReactiveFormsModule} from '@angular/forms';
import {DropdownModule} from 'primeng/dropdown';
import {FileUploadModule} from 'primeng/fileupload';
import {HttpClientModule} from '@angular/common/http';
import {InputTextModule} from 'primeng/inputtext';
import {InputNumberModule} from 'primeng/inputnumber';
import {
    ApplicationCustomsStepOneComponent
} from '@app/auth/application/application-customs-step-one/application-customs-step-one.component';
import { StepsModule } from 'primeng/steps';


@NgModule({
    declarations: [
        ApplicationCustomsStepTwoComponent
    ],
    imports: [
        CommonModule,
        ApplicationCustomsStepTwoRoutingModule,
        PrimengModule,
        TranslateModule,
        LoadingImageModule,
        ReactiveFormsModule,
        DropdownModule,
        FileUploadModule,
        HttpClientModule,
        InputTextModule,
        InputNumberModule,
        StepsModule
    ]
})
export class ApplicationCustomsStepTwoModule {}
