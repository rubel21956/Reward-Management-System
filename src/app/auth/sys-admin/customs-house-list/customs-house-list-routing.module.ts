import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CustomsHouseListComponent} from '@app/auth/sys-admin/customs-house-list/customs-house-list.component';
import {
    CustomsHouseDetailsComponent
} from '@app/auth/sys-admin/customs-house-list/component/customs-house-details/customs-house-details.component';

const routes: Routes = [
    {
        path: '',
        component: CustomsHouseListComponent
    },
    {
        path: 'customs-house-details',
        component: CustomsHouseDetailsComponent
    },
    {
        path: 'customs-house-details/:oid',
        component: CustomsHouseDetailsComponent
    },
    {
        path: 'custom-house-password-reset',
        component: CustomsHouseDetailsComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CustomsHouseListRoutingModule {
}
