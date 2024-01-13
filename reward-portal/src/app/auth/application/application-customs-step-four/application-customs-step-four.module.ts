import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ApplicationCustomsStepFourRoutingModule } from './application-customs-step-four-routing.module';
import { ApplicationCustomsStepFourComponent } from './application-customs-step-four.component';
import {PrimengModule} from '@app/common/modules/primeng-modules';
import { StepsModule } from 'primeng/steps';
import { LoadingImageModule } from '@app/common/components/loading-image/loading-image.module';
import { AppPipesModule } from "../../../common/pipes/app-pipes.module";


@NgModule({
    declarations: [
        ApplicationCustomsStepFourComponent
    ],
    imports: [
        CommonModule,
        ApplicationCustomsStepFourRoutingModule,
        PrimengModule,
        LoadingImageModule,
        StepsModule,
        AppPipesModule,
      
    ]
})
export class ApplicationCustomsStepFourModule { }
