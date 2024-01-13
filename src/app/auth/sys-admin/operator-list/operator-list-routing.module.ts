import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OperatorListComponent} from '@app/auth/sys-admin/operator-list/operator-list.component';
import { PasswordModule } from 'primeng/password';
import {
    CustomsHouseDetailsComponent
} from '@app/auth/sys-admin/customs-house-list/component/customs-house-details/customs-house-details.component';
import {OperatorDetailsComponent} from '@app/auth/sys-admin/operator-list/component/operator-details/operator-details.component';

const routes: Routes = [
    {
        path: '',
        component: OperatorListComponent
    },
    {
        path: 'operator-details',
        component: OperatorDetailsComponent
    },
    {
        path: 'operator-details/:oid',
        component: OperatorDetailsComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class OperatorListRoutingModule {
}
