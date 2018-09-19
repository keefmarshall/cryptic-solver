import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';
import { Clue } from './clue';
import 'rxjs/add/operator/map'

@Component({
  selector: 'clue-form',
  templateUrl: './clue-form.component.html',
  styleUrls: ['./clue-form.component.css']
})
export class ClueFormComponent implements OnInit {

  public clueString: string;
  public clue: Clue;
  public solution;
  public JSON;

  constructor(private http: Http) { }

  ngOnInit() {
    this.JSON = JSON;
  }

  parse() {
    this.http.get('/api/parse?clue=' + this.clueString)
      .map(res => res.json())
      .subscribe( json => {
        this.clue = json;
      });
  }

  clear() {
    this.clueString = null;
    this.clue = null;
    this.solution = null;
  }

  solve() {
    const known = this.clue.knownLetters
      .map(x => (x == null || x == " " || x == "") ? '?' : x)
      .join('');

    this.http.get('/api/solve?clue=' + this.clueString + '&known=' + known)
      .map(res => res.json())
      .subscribe( json => {
        this.solution = json;
      });
  }

  // https://github.com/angular/angular/issues/10423
  trackByIndex(index: number, value: number) {
    return index;
  }

  moveToNext($event) {
    let nextInput = $event.srcElement.nextElementSibling;
    if ($event.target.value.length > 0 && nextInput != null) {
      nextInput.focus()
    }
  }
}
