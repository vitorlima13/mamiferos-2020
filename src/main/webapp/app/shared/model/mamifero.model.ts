import { ILoja } from 'app/shared/model/loja.model';
import { TipoMamifero } from 'app/shared/model/enumerations/tipo-mamifero.model';

export interface IMamifero {
  id?: number;
  nome?: string;
  tipo?: TipoMamifero;
  apelido?: string;
  numero?: number;
  altura?: number;
  peso?: number;
  comprimento?: number;
  filhotes?: IMamifero[];
  progenitora?: IMamifero;
  loja?: ILoja;
}

export class Mamifero implements IMamifero {
  constructor(
    public id?: number,
    public nome?: string,
    public tipo?: TipoMamifero,
    public apelido?: string,
    public numero?: number,
    public altura?: number,
    public peso?: number,
    public comprimento?: number,
    public filhotes?: IMamifero[],
    public progenitora?: IMamifero,
    public loja?: ILoja
  ) {}
}
