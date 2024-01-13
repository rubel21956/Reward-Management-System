import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {CreateOfficeRoutingModule} from './create-office-routing.module';
import {CreateOfficeComponent} from './create-office.component';
import {TranslateModule} from '@ngx-translate/core';
import {LoadingImageModule} from '@app/common/components/loading-image/loading-image.module';
import {PrimengModule} from '@app/common/modules/primeng-modules';
import {ReactiveFormsModule} from '@angular/forms';


@NgModule({
    declarations: [
        CreateOfficeComponent
    ],
    imports: [
        CommonModule,
        TranslateModule,
        LoadingImageModule,
        PrimengModule,
        ReactiveFormsModule,
        CreateOfficeRoutingModule
    ]
})
export class CreateOfficeModule {
}
