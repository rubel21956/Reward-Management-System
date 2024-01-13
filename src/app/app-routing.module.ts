import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './auth/login/login.component';
import {HomeComponent} from './auth/home/home.component';
import {AuthGuard} from './auth/auth.guard';
import {HomeResolver} from './auth/home/home.resolver';
import { Keyboar } from './auth/keyBoard/keyboard.component';


const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
        canActivate: [AuthGuard],
        resolve: {home: HomeResolver},
        children: [
            {
                path: 'dashboard', redirectTo: 'dashboard', pathMatch: 'full',
            },
            {
                path: 'change-password',
                loadChildren: () => import('./security/change-password/change-password.module').then(mod => mod.ChangePasswordModule)
            },
            {
                path: 'my-profile',
                loadChildren: () => import('./auth/my-profile/my-profile.module').then(mod => mod.MyProfileModule)
            },
            {
                path: 'create-office',
                loadChildren: () => import('./auth/office/create-office/create-office.module').then(mod => mod.CreateOfficeModule)
            },
            {
                path: 'manage-office',
                loadChildren: () => import('./auth/office/office-list/office-list.module').then(mod => mod.OfficeListModule)
            },
            {
                path: 'application-customs-step-one',
                loadChildren: () => import('./auth/application/application-customs-step-one/application-customs-step-one.module').then(mod => mod.ApplicationCustomsStepOneModule)
            },
            {
                path: 'application-customs-step-two',
                loadChildren: () => import('./auth/application/application-customs-step-two/application-customs-step-two.module').then(mod => mod.ApplicationCustomsStepTwoModule)
            },
            {
                path: 'application-customs-step-four',
                loadChildren: () => import('./auth/application/application-customs-step-four/application-customs-step-four.module').then(mod => mod.ApplicationCustomsStepFourModule)
            },
            {
                path: 'application-customs-step-five',
                loadChildren: () => import('./auth/application/application-customs-step-five/application-customs-step-five.module').then(mod => mod.ApplicationCustomsStepFiveModule)
            },
            {
                path: 'application-customs-list',
                loadChildren: () => import('./auth/application/application-customs-list/application-customs-list.module').then(mod => mod.ApplicationCustomsListModule)
            },
            {
                path: 'application-list',
                loadChildren: () => import('./auth/nbr-admin/application-list/application-list.module').then(mod => mod.ApplicationListModule)
            },
            {
                path: 'application-details-step-one',
                loadChildren: () => import('./auth/nbr-admin/application-details-step-one/application-details-step-one.module').then(mod => mod.ApplicationDetailsStepOneModule)
            },
            {
                path: 'application-details-step-two',
                loadChildren: () => import('./auth/nbr-admin/application-details-step-two/application-details-step-two.module').then(mod => mod.ApplicationDetailsStepTwoModule)
            },
            {
                path: 'application-details-step-three',
                loadChildren: () => import('./auth/nbr-admin/application-details-step-three/application-details-step-three.module').then(mod => mod.ApplicationDetailsStepThreeModule)
            },
            {   
                path: 'customs-house-list',
                loadChildren: () => import('./auth/sys-admin/customs-house-list/customs-house-list.module').then(mod => mod.CustomsHouseListModule)
            },
            {
                path: 'operator-list',
                loadChildren: () => import('./auth/sys-admin/operator-list/operator-list.module').then(mod => mod.OperatorListModule)
            },
            {
                path: 'keyboard', component: Keyboar, pathMatch: 'full'
            },
            {
                path: 'password-recovery', 
                loadChildren: () => import('./auth/sys-admin/password-recovery/password-recovery.module').then(mod => mod.PasswordRecoveryModule)
            },
            {
                path: 'dashboard',
                loadChildren: () => import('./auth/dashboard/dashboard.module').then(mod => mod.DashboardModule)
            },
            {
                path: 'archive',
                loadChildren: () => import('./auth/nbr-admin/application-list/component/archive/archive.module').then(mod => mod.ArchiveModule)
            },
            {
                path: 'operatorArchive',
                loadChildren: () => import('./auth/application/operator-archive/operator-archive.module').then(mod => mod.OperatorArchiveModule)
            }
            

        ]
    },
    {
        path: 'login',
        children: [
            {path: '', component: LoginComponent}
        ]
    },  
    
    {
        path: 'forgot-userid-password',
        loadChildren: () => import('./security/forgot-userid-password/forgot-userid-password.module').then(mod => mod.ForgotUseridPasswordModule)
    },
    {
        path: 'session-expired',
        loadChildren: () => import('./security/session-expired/session-expired.module').then(mod => mod.SessionExpiredModule)
    },
    {
        path: 'page-not-found',
        loadChildren: () => import('./security/page-not-found/page-not-found.module').then(mod => mod.PageNotFoundModule)
    },
    // {
    //     path: '**',
    //     redirectTo: 'page-not-found'
    // }
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules, relativeLinkResolution: 'legacy'})],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
