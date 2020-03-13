import { Moment } from 'moment';

export interface IObservacaoInseto {
  id?: number;
  data?: Moment;
  local?: string;
  quantidade?: number;
}

export class ObservacaoInseto implements IObservacaoInseto {
  constructor(public id?: number, public data?: Moment, public local?: string, public quantidade?: number) {}
}
