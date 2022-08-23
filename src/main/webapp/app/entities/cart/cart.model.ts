import { IUserExtra } from 'app/entities/user-extra/user-extra.model';

export interface ICart {
  id: number;
  user?: Pick<IUserExtra, 'id'> | null;
}

export type NewCart = Omit<ICart, 'id'> & { id: null };
