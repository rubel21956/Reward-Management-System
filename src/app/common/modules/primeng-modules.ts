import { NgModule } from '@angular/core';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { RadioButtonModule } from 'primeng/radiobutton';
import { FieldsetModule } from 'primeng/fieldset';
import { CheckboxModule } from 'primeng/checkbox';
import { CalendarModule } from 'primeng/calendar';
import { TableModule } from 'primeng/table';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ProgressBarModule } from 'primeng/progressbar';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { TooltipModule } from 'primeng/tooltip';
import { PasswordModule } from 'primeng/password';
import { AccordionModule } from 'primeng/accordion';
import { DialogModule } from 'primeng/dialog';
import {CarouselModule} from 'primeng/carousel';
import { MessageService, ConfirmationService } from 'primeng/api';
import { InputText, InputTextModule } from 'primeng/inputtext';

@NgModule({
    imports: [],
    declarations: [],
    exports: [
        CalendarModule,
        TableModule,
        MessageModule,
        ToastModule,
        ConfirmDialogModule,
        ProgressBarModule,
        DropdownModule,
        ButtonModule,
        TooltipModule,
        PasswordModule,
        AccordionModule,
        InputTextareaModule,
        InputTextModule,
        RadioButtonModule,
        FieldsetModule,
        CheckboxModule,
        DialogModule,
        CarouselModule
    ],
    providers: [MessageService, ConfirmationService]
})
export class PrimengModule {

}