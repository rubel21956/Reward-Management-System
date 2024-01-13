import {TranslateModule} from '@ngx-translate/core';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardComponent} from './dashboard.component';
import {DashboardRoutingModule} from './dashboard-routing.module';
import {LoadingImageModule} from '@app/common/components/loading-image/loading-image.module';
import {TableModule} from 'primeng/table';
import {PrimengModule} from '@app/common/modules/primeng-modules';
import {FileUploadModule} from 'primeng/fileupload';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AutoCompleteModule} from 'primeng/autocomplete';
import {CalendarModule} from 'primeng/calendar';
import {NgApexchartsModule} from 'ng-apexcharts';

@NgModule({
    declarations: [
        DashboardComponent
    ],
    imports: [
        CommonModule,
        LoadingImageModule,
        TranslateModule,
        DashboardRoutingModule,
        TableModule,
        CommonModule,
        TranslateModule,
        LoadingImageModule,
        PrimengModule,
        FileUploadModule,
        ReactiveFormsModule,
        FormsModule,
        AutoCompleteModule,
        CalendarModule,
        NgApexchartsModule
    ]
})
export class DashboardModule {
}
