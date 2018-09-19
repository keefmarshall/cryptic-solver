import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { ClueFormComponent } from './clue-form/clue-form.component';
import { WordListComponent } from './word-list/word-list.component';

@NgModule({
  declarations: [
    AppComponent,
    ClueFormComponent,
    WordListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
