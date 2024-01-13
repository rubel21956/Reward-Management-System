import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UpdateOfficeComponent} from '../components/update-office/update-office.component';
import {OfficeListComponent} from './office-list.component';
import {CreateOfficeComponent} from '@app/auth/office/create-office/create-office.component';

const routes: Routes = [
    {
        path: '',
        children: [
            {
                path: '',
                component: OfficeListComponent
            },
            {
                path: 'createoffice',
                component: CreateOfficeComponent
            },
            {
                path: 'update-office/:oid',
                component: UpdateOfficeComponent
            }


        ],
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class OfficeListRoutingModule {
}
