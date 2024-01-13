import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomsHouseListRoutingModule } from './customs-house-list-routing.module';
import { CustomsHouseListComponent } from './customs-house-list.component';
import {TableModule} from 'primeng/table';
import {ReactiveFormsModule} from '@angular/forms';
import { CustomsHouseDetailsComponent } from './component/customs-house-details/customs-house-details.component';
import {PrimengModule} from '@app/common/modules/primeng-modules';



@NgModule({
  declarations: [
    CustomsHouseListComponent,
    CustomsHouseDetailsComponent,
    
  ],
    imports: [
        CommonModule,
        CustomsHouseListRoutingModule,
        TableModule,
        ReactiveFormsModule,
        PrimengModule
    ]
})
export class CustomsHouseListModule { }
