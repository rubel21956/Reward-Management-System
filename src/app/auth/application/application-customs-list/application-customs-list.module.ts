import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ApplicationCustomsListRoutingModule } from './application-customs-list-routing.module';
import { ApplicationCustomsListComponent } from './application-customs-list.component';
import {ReactiveFormsModule} from '@angular/forms';
import {CalendarModule} from 'primeng/calendar';
import {DropdownModule} from 'primeng/dropdown';
import {TableModule} from 'primeng/table';
import {InputTextModule} from 'primeng/inputtext';
import { LoadingImageModule } from '@app/common/components/loading-image/loading-image.module';


@NgModule({
  declarations: [
    ApplicationCustomsListComponent
  ],
    imports: [
        CommonModule,
        ApplicationCustomsListRoutingModule,
        ReactiveFormsModule,
        CalendarModule,
        DropdownModule,
        TableModule,
        InputTextModule,
        LoadingImageModule
    ]
})
export class ApplicationCustomsListModule { }
