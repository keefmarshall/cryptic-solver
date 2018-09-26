import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { ClueFormComponent } from './clue-form/clue-form.component';
import { WordListComponent } from './word-list/word-list.component';
import { AnagramFormComponent } from './anagram-form/anagram-form.component';
import {LetterHelperService} from "./services/letter-helper.service";
import { PatternFormComponent } from './pattern-form/pattern-form.component';

@NgModule({
  declarations: [
    AppComponent,
    ClueFormComponent,
    WordListComponent,
    AnagramFormComponent,
    PatternFormComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [LetterHelperService],
  bootstrap: [AppComponent]
})
export class AppModule { }
