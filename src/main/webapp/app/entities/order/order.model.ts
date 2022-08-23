import dayjs from 'dayjs/esm';
import { IUserExtra } from 'app/entities/user-extra/user-extra.model';

export interface IOrder {
  id: number;
  orderDate?: dayjs.Dayjs | null;
  quantity?: number | null;
  orderStatus?: string | null;
  donor?: Pick<IUserExtra, 'id'> | null;
  recipient?: Pick<IUserExtra, 'id'> | null;
}

export type NewOrder = Omit<IOrder, 'id'> & { id: null };
