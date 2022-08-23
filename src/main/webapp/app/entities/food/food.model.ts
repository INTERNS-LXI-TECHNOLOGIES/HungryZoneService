import dayjs from 'dayjs/esm';
import { ICategory } from 'app/entities/category/category.model';
import { IUserExtra } from 'app/entities/user-extra/user-extra.model';
import { IOrder } from 'app/entities/order/order.model';

export interface IFood {
  id: number;
  name?: string | null;
  expiry?: dayjs.Dayjs | null;
  remainingQty?: number | null;
  description?: string | null;
  imageUrl?: string | null;
  category?: Pick<ICategory, 'id'> | null;
  donor?: Pick<IUserExtra, 'id'> | null;
  order?: Pick<IOrder, 'id'> | null;
}

export type NewFood = Omit<IFood, 'id'> & { id: null };
