import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CreateOfficeComponent} from './create-office.component';

const routes: Routes = [
    {
        path: '',
        component: CreateOfficeComponent
    }

];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CreateOfficeRoutingModule {
}
