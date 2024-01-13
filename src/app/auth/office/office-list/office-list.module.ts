import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {OfficeListRoutingModule} from './office-list-routing.module';
import {OfficeListComponent} from './office-list.component';
import {TranslateModule} from '@ngx-translate/core';
import {LoadingImageModule} from '@app/common/components/loading-image/loading-image.module';
import {PrimengModule} from '@app/common/modules/primeng-modules';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {UpdateOfficeComponent} from '../components/update-office/update-office.component';
import {AutoCompleteModule} from 'primeng/autocomplete';
import {CalendarModule} from 'primeng/calendar';
import {FileUploadModule} from 'primeng/fileupload';


@NgModule({
    declarations: [
        OfficeListComponent,
        UpdateOfficeComponent
    ],
    imports: [
        CommonModule,
        TranslateModule,
        LoadingImageModule,
        OfficeListRoutingModule,
        PrimengModule,
        FileUploadModule,
        ReactiveFormsModule,
        FormsModule,
        AutoCompleteModule,
        CalendarModule
    ]
})
export class OfficeListModule {
}
