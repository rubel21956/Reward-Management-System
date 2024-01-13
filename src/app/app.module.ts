import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpBackend, HttpClient, HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {AppRoutingModule} from './app-routing.module';
import {SharedModule} from './common/components/shared.module';
import {AppComponent} from './app.component';
import {LoadingImageModule} from './common/components/loading-image/loading-image.module';
import {OAuth2Service} from './common/services/oauth.service';
import {TokenInterceptorService} from './common/services/token-interceptor.service';
import {SessionService} from './common/services/session.service';
import {ServiceWorkerModule} from '@angular/service-worker';
import {environment} from '../environments/environment';
import {HomeComponent} from './auth/home/home.component';
import {LoginComponent} from './auth/login/login.component';
import {PinchZoomModule} from 'ngx-pinch-zoom';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RadioButtonModule} from 'primeng/radiobutton';
import {PasswordModule} from 'primeng/password';
import {ToastModule} from 'primeng/toast';
import {DropdownModule} from 'primeng/dropdown';
import {CheckboxModule} from 'primeng/checkbox';
import {CalendarModule} from 'primeng/calendar';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {NgApexchartsModule} from 'ng-apexcharts';
import { PrimengModule } from './common/modules/primeng-modules';
import { ApplicationDetailsStepOneComponent } from './auth/nbr-admin/application-details-step-one/application-details-step-one.component';
import { ViewApplicationCustomsStepOneComponent } from './auth/application/component/view-application-customs-step-one/view-application-customs-step-one.component';
import { UpdateApplicationCustomsStepOneComponent } from './auth/application/component/update-application-customs-step-one/update-application-customs-step-one.component';
import { HotTableModule } from '@handsontable/angular';
import { registerAllModules } from 'handsontable/registry';
import { StepsModule } from 'primeng/steps';
import { PdfJsViewerModule } from 'ng2-pdfjs-viewer';
import { Keyboar } from './auth/keyBoard/keyboard.component';
import { MessageModule } from 'primeng/message';


export function createTranslateLoader(httpBackend: HttpBackend) {
    return new TranslateHttpLoader(new HttpClient(httpBackend), './assets/i18n/', '.json');
}

registerAllModules();

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent,
        LoginComponent,
        Keyboar,
        ViewApplicationCustomsStepOneComponent,
        UpdateApplicationCustomsStepOneComponent,
        
    ],
    imports: [
        BrowserModule,
        SharedModule,
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: createTranslateLoader,
                deps: [HttpBackend],
            },            
        }),
        BrowserAnimationsModule,
        AppRoutingModule,
        HttpClientModule,
        LoadingImageModule,
        ServiceWorkerModule.register('ngsw-worker.js', {
            enabled: environment.production,
            // Register the ServiceWorker as soon as the app is stable
            // or after 30 second   s (whichever comes first).
            registrationStrategy: 'registerWhenStable:30000'
        }),
        PinchZoomModule,
        ReactiveFormsModule,
        RadioButtonModule,
        PasswordModule,
        ToastModule,
        FormsModule,
        DropdownModule,
        CheckboxModule,
        CalendarModule,
        ConfirmDialogModule,
        NgApexchartsModule,
        PrimengModule,
        HotTableModule.forRoot(),
        StepsModule,
        PdfJsViewerModule, 
        MessageModule
           
    ],
    providers: [OAuth2Service, SessionService, 
        {
            provide: HTTP_INTERCEPTORS,
            useClass: TokenInterceptorService,
            multi: true
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
