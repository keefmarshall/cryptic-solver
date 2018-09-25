import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';
import { Clue } from './clue';
import 'rxjs/add/operator/map'
import {LetterHelperService} from "../services/letter-helper.service";

@Component({
  selector: 'clue-form',
  templateUrl: './clue-form.component.html',
  styleUrls: ['./clue-form.component.css']
})
export class ClueFormComponent implements OnInit {

  public clueString: string;
  public clue: Clue;
  public solution;

  constructor(
    private http: Http,
    public letterHelperService: LetterHelperService
  ) { }

  ngOnInit() {
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
    const known = this.letterHelperService.letterArrayToString(this.clue.knownLetters);

    this.http.get('/api/solve?clue=' + this.clueString + '&known=' + known)
      .map(res => res.json())
      .subscribe( json => {
        this.solution = json;
      });
  }

}
