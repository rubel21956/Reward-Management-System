import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DropdownModule} from 'primeng/dropdown';
import {ApplicationListRoutingModule} from './application-list-routing.module';
import {ApplicationListComponent} from './application-list.component';
import {TableModule} from 'primeng/table';
import {CalendarModule} from 'primeng/calendar';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ApplicationDetailsStepOneComponent} from './component/application-details-step-one/application-details-step-one.component';
import {ApplicationDetailsStepTwoComponent} from './component/application-details-step-two/application-details-step-two.component';
import {ApplicationDetailsStepThreeComponent} from './component/application-details-step-three/application-details-step-three.component';
import {ToastModule} from 'primeng/toast';
import {CheckboxModule} from 'primeng/checkbox';
import {InputSwitchModule} from 'primeng/inputswitch';

import { ApplicationDetailsStepTwoAcceptedComponent } from './component/application-details-step-two-accepted/application-details-step-two-accepted.component';
import { PrimengModule } from '@app/common/modules/primeng-modules';
import { TranslateModule } from '@ngx-translate/core';
import { AppPipesModule } from "../../../common/pipes/app-pipes.module";
import { LoadingImageModule } from "../../../common/components/loading-image/loading-image.module";
import { ArchiveComponent } from './component/archive/archive.component';

@NgModule({
    declarations: [
        ApplicationListComponent,
        ApplicationDetailsStepOneComponent,
        ApplicationDetailsStepTwoComponent,
        ApplicationDetailsStepThreeComponent,
        ApplicationDetailsStepTwoAcceptedComponent,
        ArchiveComponent
    ],
    imports: [
        CommonModule,
        ApplicationListRoutingModule,
        DropdownModule,
        TableModule,
        CalendarModule,
        ReactiveFormsModule,
        ToastModule,
        CheckboxModule,
        InputSwitchModule,
        FormsModule,
        PrimengModule,
        TranslateModule,
        AppPipesModule,
        LoadingImageModule
    ]
})
export class ApplicationListModule {
}
