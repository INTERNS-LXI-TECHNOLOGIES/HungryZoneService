import { IUser } from 'app/entities/user/user.model';

export interface IUserExtra {
  id: number;
  phoneNumber?: string | null;
  address?: string | null;
  locationAtXAxis?: string | null;
  locationAtYAxis?: string | null;
  user?: Pick<IUser, 'id'> | null;
}

export type NewUserExtra = Omit<IUserExtra, 'id'> & { id: null };
