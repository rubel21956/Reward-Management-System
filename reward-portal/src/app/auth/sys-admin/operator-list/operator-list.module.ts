import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {OperatorListRoutingModule} from './operator-list-routing.module';
import {OperatorListComponent} from './operator-list.component';
import {TableModule} from 'primeng/table';
import {ReactiveFormsModule} from '@angular/forms';
import {OperatorDetailsComponent} from '@app/auth/sys-admin/operator-list/component/operator-details/operator-details.component';
import {CalendarModule} from 'primeng/calendar';
import {ToastModule} from 'primeng/toast';
import {DropdownModule} from 'primeng/dropdown';
import {DialogModule} from 'primeng/dialog';



@NgModule({
    declarations: [
        OperatorListComponent,
        OperatorDetailsComponent,
        
    ],
    imports: [
        CommonModule,
        OperatorListRoutingModule,
        TableModule,
        ReactiveFormsModule,
        CalendarModule,
        ToastModule,
        DropdownModule,
        DialogModule,
        
    ]
})
export class OperatorListModule {
}
