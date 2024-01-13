import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OperatorArchiveRoutingModule } from './operator-archive-routing.module';
import { ArchiveComponent } from './archive/archive.component';
import { ReactiveFormsModule } from '@angular/forms';
import { LoadingImageModule } from '@app/common/components/loading-image/loading-image.module';
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { RejectedApplicationComponent } from './rejected-application/rejected-application.component';


@NgModule({
  declarations: [
    ArchiveComponent,
    RejectedApplicationComponent
  ],
  imports: [
    CommonModule,
    OperatorArchiveRoutingModule,
    ReactiveFormsModule,
        CalendarModule,
        DropdownModule,
        TableModule,
        InputTextModule,
        LoadingImageModule
  ]
})
export class OperatorArchiveModule { }
