/*
 * Copyright (C) 2012 Ondrej Perutka
 *
 * This program is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public 
 * License as published by the Free Software Foundation, either 
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library. If not, see 
 * <http://www.gnu.org/licenses/>.
 */
package org.libav.avutil.bridge;

import java.nio.charset.Charset;
import org.bridj.Pointer;
import org.libav.LibavException;
import org.libav.bridge.LibraryManager;
import org.libav.util.Rational;

/**
 * Convenience class to work with AVOption structs.
 * 
 * @author Ondrej Perutka
 */
public class AVOptionHandler {
    
    public static final int AV_OPT_SEARCH_CHILDREN = 0x0001;
    public static final int AV_OPT_SEARCH_FAKE_OBJ = 0x0002;
    
    private static final AVUtilLibrary utilLib;
    
    private static final Charset UTF8;
    
    private static final IGetStringFunction getStringFunction;
    private static final IGetLongFunction getLongFunction;
    private static final IGetDoubleFunction getDoubleFunction;
    private static final IGetRationalFunction getRationalFunction;
    
    private static final ISetStringFunction setStringFunction;
    private static final ISetLongFunction setLongFunction;
    private static final ISetDoubleFunction setDoubleFunction;
    
    private static final Pointer<Long> longValue;
    private static final Pointer<Double> doubleValue;
    private static final Pointer<Pointer<?>> pointerValue;
    private static final Pointer<?> qValue;
    
    static {
        utilLib = LibraryManager.getInstance().getAVUtilLibrary();
        
        UTF8 = Charset.forName("UTF-8");
        
        if (utilLib.functionExists("av_opt_get"))
            getStringFunction = new OptGetStringFunction();
        else
            getStringFunction = new GetStringFunction();
        
        if (utilLib.functionExists("av_opt_get_int"))
            getLongFunction = new OptGetLongFunction();
        else
            getLongFunction = new GetLongFunction();
        
        if (utilLib.functionExists("av_opt_get_double"))
            getDoubleFunction = new OptGetDoubleFunction();
        else
            getDoubleFunction = new GetDoubleFunction();
        
        if (utilLib.functionExists("av_opt_get_q"))
            getRationalFunction = new OptGetRationalFunction();
        else
            getRationalFunction = new GetRationalFunction();
        
        if (utilLib.functionExists("av_opt_set"))
            setStringFunction = new OptSetStringFunction();
        else
            setStringFunction = new SetStringFunction();
        
        if (utilLib.functionExists("av_opt_set_int"))
            setLongFunction = new OptSetLongFunction();
        else
            setLongFunction = new SetLongFunction();
        
        if (utilLib.functionExists("av_opt_set_double"))
            setDoubleFunction = new OptSetDoubleFunction();
        else
            setDoubleFunction = new SetDoubleFunction();
        
        longValue = Pointer.allocateLong();
        doubleValue = Pointer.allocateDouble();
        pointerValue = Pointer.allocatePointer();
        qValue = Pointer.pointerTo(new AVRational());
    }
    
    /**
     * Get string option from an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @return option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static String getString(Pointer<?> obj, String name) throws LibavException {
        return getString(obj, name, 0);
    }
    
    /**
     * Get string option from an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param searchFlags a mix of AV_OPT_SEARCH_ flags
     * @return option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static String getString(Pointer<?> obj, String name, int searchFlags) throws LibavException {
        return getStringFunction.getString(obj, name, searchFlags);
    }
    
    /**
     * Get byte option from an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @return option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static byte getByte(Pointer<?> obj, String name) throws LibavException {
        return (byte)getLong(obj, name);
    }
    
    /**
     * Get byte option from an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param searchFlags a mix of AV_OPT_SEARCH_ flags
     * @return option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static byte getByte(Pointer<?> obj, String name, int searchFlags) throws LibavException {
        return (byte)getLong(obj, name, searchFlags);
    }
    
    /**
     * Get short option from an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @return option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static short getShort(Pointer<?> obj, String name) throws LibavException {
        return (short)getLong(obj, name);
    }
    
    /**
     * Get short option from an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param searchFlags a mix of AV_OPT_SEARCH_ flags
     * @return option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static short getShort(Pointer<?> obj, String name, int searchFlags) throws LibavException {
        return (short)getLong(obj, name, searchFlags);
    }
    
    /**
     * Get int option from an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @return option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static int getInt(Pointer<?> obj, String name) throws LibavException {
        return (int)getLong(obj, name);
    }
    
    /**
     * Get int option from an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param searchFlags a mix of AV_OPT_SEARCH_ flags
     * @return option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static int getInt(Pointer<?> obj, String name, int searchFlags) throws LibavException {
        return (int)getLong(obj, name, searchFlags);
    }
    
    /**
     * Get long option from an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @return option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static long getLong(Pointer<?> obj, String name) throws LibavException {
        return getLong(obj, name, 0);
    }
    
    /**
     * Get long option from an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param searchFlags a mix of AV_OPT_SEARCH_ flags
     * @return option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static long getLong(Pointer<?> obj, String name, int searchFlags) throws LibavException {
        return getLongFunction.getLong(obj, name, searchFlags);
    }
    
    /**
     * Get float option from an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @return option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static float getFloat(Pointer<?> obj, String name) throws LibavException {
        return (float)getDouble(obj, name);
    }
    
    /**
     * Get float option from an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param searchFlags a mix of AV_OPT_SEARCH_ flags
     * @return option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static float getFloat(Pointer<?> obj, String name, int searchFlags) throws LibavException {
        return (float)getDouble(obj, name, searchFlags);
    }
    
    /**
     * Get double option from an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @return option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static double getDouble(Pointer<?> obj, String name) throws LibavException {
        return getDouble(obj, name, 0);
    }
    
    /**
     * Get double option from an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param searchFlags a mix of AV_OPT_SEARCH_ flags
     * @return option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static double getDouble(Pointer<?> obj, String name, int searchFlags) throws LibavException {
        return getDoubleFunction.getDouble(obj, name, searchFlags);
    }
    
    /**
     * Get rational option from an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @return option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static Rational getRational(Pointer<?> obj, String name) throws LibavException {
        return getRational(obj, name, 0);
    }
    
    /**
     * Get rational option from an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param searchFlags a mix of AV_OPT_SEARCH_ flags
     * @return option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static Rational getRational(Pointer<?> obj, String name, int searchFlags) throws LibavException {
        return getRationalFunction.getRational(obj, name, searchFlags);
    }
    
    /**
     * Set string option of an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param value option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static void setString(Pointer<?> obj, String name, String value) throws LibavException {
        setString(obj, name, value, 0);
    }
    
    /**
     * Set string option of an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param value option value
     * @param searchFlags a mix of AV_OPT_SEARCH_ flags
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static void setString(Pointer<?> obj, String name, String value, int searchFlags) throws LibavException {
        setStringFunction.setString(obj, name, value, searchFlags);
    }
    
    /**
     * Set byte option of an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param value option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static void setByte(Pointer<?> obj, String name, byte value) throws LibavException {
        setLong(obj, name, value);
    }
    
    /**
     * Set byte option of an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param value option value
     * @param searchFlags a mix of AV_OPT_SEARCH_ flags
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static void setByte(Pointer<?> obj, String name, byte value, int searchFlags) throws LibavException {
        setLong(obj, name, value, searchFlags);
    }
    
    /**
     * Set short option of an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param value option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static void setShort(Pointer<?> obj, String name, short value) throws LibavException {
        setLong(obj, name, value);
    }
    
    /**
     * Set short option of an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param value option value
     * @param searchFlags a mix of AV_OPT_SEARCH_ flags
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static void setShort(Pointer<?> obj, String name, short value, int searchFlags) throws LibavException {
        setLong(obj, name, value, searchFlags);
    }
    
    /**
     * Set int option of an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param value option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static void setInt(Pointer<?> obj, String name, int value) throws LibavException {
        setLong(obj, name, value);
    }
    
    /**
     * Set int option of an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param value option value
     * @param searchFlags a mix of AV_OPT_SEARCH_ flags
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static void setInt(Pointer<?> obj, String name, int value, int searchFlags) throws LibavException {
        setLong(obj, name, value, searchFlags);
    }
    
    /**
     * Set long option of an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param value option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static void setLong(Pointer<?> obj, String name, long value) throws LibavException {
        setLong(obj, name, value, 0);
    }
    
    /**
     * Set long option of an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param value option value
     * @param searchFlags a mix of AV_OPT_SEARCH_ flags
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static void setLong(Pointer<?> obj, String name, long value, int searchFlags) throws LibavException {
        setLongFunction.setLong(obj, name, value, searchFlags);
    }
    
    /**
     * Set float option of an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param value option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static void setFloat(Pointer<?> obj, String name, float value) throws LibavException {
        setDouble(obj, name, value);
    }
    
    /**
     * Set float option of an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param value option value
     * @param searchFlags a mix of AV_OPT_SEARCH_ flags
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static void setFloat(Pointer<?> obj, String name, float value, int searchFlags) throws LibavException {
        setDouble(obj, name, value, searchFlags);
    }
    
    /**
     * Set double option of an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param value option value
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static void setDouble(Pointer<?> obj, String name, double value) throws LibavException {
        setDouble(obj, name, value, 0);
    }
    
    /**
     * Set double option of an object which has pointer to an AVClass as its
     * first field.
     * 
     * @param obj an object
     * @param name option name
     * @param value option value
     * @param searchFlags a mix of AV_OPT_SEARCH_ flags
     * @throws LibavException if an error occurs (for example there is no such
     * option)
     */
    public static void setDouble(Pointer<?> obj, String name, double value, int searchFlags) throws LibavException {
        setDoubleFunction.setDouble(obj, name, value, searchFlags);
    }
    
    private static interface IGetStringFunction {
        String getString(Pointer<?> obj, String name, int searchFlags) throws LibavException;
    }
    
    private static interface IGetLongFunction {
        long getLong(Pointer<?> obj, String name, int searchFlags) throws LibavException;
    }
    
    private static interface IGetDoubleFunction {
        double getDouble(Pointer<?> obj, String name, int searchFlags) throws LibavException;
    }
    
    private static interface IGetRationalFunction {
        Rational getRational(Pointer<?> obj, String name, int searchFlags) throws LibavException;
    }
    
    private static interface ISetStringFunction {
        void setString(Pointer<?> obj, String name, String value, int searchFlags) throws LibavException;
    }
    
    private static interface ISetLongFunction {
        void setLong(Pointer<?> obj, String name, long value, int searchFlags) throws LibavException;
    }
    
    private static interface ISetDoubleFunction {
        void setDouble(Pointer<?> obj, String name, double value, int searchFlags) throws LibavException;
    }
    
    private static class GetStringFunction implements IGetStringFunction {
        private static final int BUFFER_LEN = 256;
        
        @Override
        public String getString(Pointer<?> obj, String name, int searchFlags) throws LibavException {
            Pointer<?> tmp = Pointer.pointerToString(name, Pointer.StringType.C, UTF8);
            Pointer<Byte> buffer = Pointer.allocateBytes(BUFFER_LEN);
        
            synchronized (pointerValue) {
                pointerValue.set(null);
                utilLib.av_get_string(obj, tmp.as(Byte.class), pointerValue, buffer, BUFFER_LEN);
                if (pointerValue.get() == null)
                    throw new LibavException("there is no option with name " + name);
            }

            return buffer.getString(Pointer.StringType.C, UTF8);
        }
    }
    
    private static class OptGetStringFunction implements IGetStringFunction {
        @Override
        public String getString(Pointer<?> obj, String name, int searchFlags) throws LibavException {
            Pointer<?> tmp = Pointer.pointerToString(name, Pointer.StringType.C, UTF8);
        
            synchronized (pointerValue) {
                int res = utilLib.av_opt_get(obj, tmp.as(Byte.class), searchFlags, pointerValue);
                if (res != 0)
                    throw new LibavException(res);

                tmp = pointerValue.get();
            }

            String result = tmp.getString(Pointer.StringType.C, UTF8);
            utilLib.av_free(tmp);

            return result;
        }
    }
    
    private static class GetLongFunction implements IGetLongFunction {
        @Override
        public long getLong(Pointer<?> obj, String name, int searchFlags) throws LibavException {
            Pointer<?> tmp = Pointer.pointerToString(name, Pointer.StringType.C, UTF8);
            
            long result;
            synchronized (pointerValue) {
                pointerValue.set(null);
                result = utilLib.av_get_int(obj, tmp.as(Byte.class), pointerValue);
                if (pointerValue.get() == null)
                    throw new LibavException("there is no option with name " + name);
            }
            
            return result;
        }
    }
    
    private static class OptGetLongFunction implements IGetLongFunction {
        @Override
        public long getLong(Pointer<?> obj, String name, int searchFlags) throws LibavException {
            Pointer<?> tmp = Pointer.pointerToString(name, Pointer.StringType.C, UTF8);
            
            synchronized (longValue) {
                int res = utilLib.av_opt_get_int(obj, tmp.as(Byte.class), searchFlags, longValue);
                if (res != 0)
                    throw new LibavException(res);

                return longValue.get();
            }
        }
    }
    
    private static class GetDoubleFunction implements IGetDoubleFunction {
        @Override
        public double getDouble(Pointer<?> obj, String name, int searchFlags) throws LibavException {
            Pointer<?> tmp = Pointer.pointerToString(name, Pointer.StringType.C, UTF8);
            
            double result;
            synchronized (pointerValue) {
                pointerValue.set(null);
                result = utilLib.av_get_double(obj, tmp.as(Byte.class), pointerValue);
                if (pointerValue.get() == null)
                    throw new LibavException("there is no option with name " + name);
            }
            
            return result;
        }
    }
    
    private static class OptGetDoubleFunction implements IGetDoubleFunction {
        @Override
        public double getDouble(Pointer<?> obj, String name, int searchFlags) throws LibavException {
            Pointer<?> tmp = Pointer.pointerToString(name, Pointer.StringType.C, UTF8);

            synchronized (doubleValue) {
                int res = utilLib.av_opt_get_double(obj, tmp.as(Byte.class), searchFlags, doubleValue);
                if (res != 0)
                    throw new LibavException(res);

                return longValue.get();
            }
        }
    }
    
    private static class GetRationalFunction implements IGetRationalFunction {
        @Override
        public Rational getRational(Pointer<?> obj, String name, int searchFlags) throws LibavException {
            throw new UnsupportedOperationException("not supported");
        }
    }
    
    private static class OptGetRationalFunction implements IGetRationalFunction {
        @Override
        public Rational getRational(Pointer<?> obj, String name, int searchFlags) throws LibavException {
            Pointer<?> tmp = Pointer.pointerToString(name, Pointer.StringType.C, UTF8);

            synchronized (qValue) {
                int res = utilLib.av_opt_get_q(obj, tmp.as(Byte.class), searchFlags, qValue);
                if (res != 0)
                    throw new LibavException(res);

                return new Rational(new AVRational(qValue));
            }
        }
    }
    
    private static class SetStringFunction implements ISetStringFunction {
        @Override
        public void setString(Pointer<?> obj, String name, String value, int searchFlags) throws LibavException {
            Pointer<?> tmp1 = Pointer.pointerToString(name, Pointer.StringType.C, UTF8);
            Pointer<?> tmp2 = Pointer.pointerToString(value, Pointer.StringType.C, UTF8);

            int res = utilLib.av_set_string3(obj, tmp1.as(Byte.class), tmp2.as(Byte.class), 0, null);
            if (res != 0)
                throw new LibavException(res);
        }
    }
    
    private static class OptSetStringFunction implements ISetStringFunction {
        @Override
        public void setString(Pointer<?> obj, String name, String value, int searchFlags) throws LibavException {
            Pointer<?> tmp1 = Pointer.pointerToString(name, Pointer.StringType.C, UTF8);
            Pointer<?> tmp2 = Pointer.pointerToString(value, Pointer.StringType.C, UTF8);

            int res = utilLib.av_opt_set(obj, tmp1.as(Byte.class), tmp2.as(Byte.class), searchFlags);
            if (res != 0)
                throw new LibavException(res);
        }
    }
    
    private static class SetLongFunction implements ISetLongFunction {
        @Override
        public void setLong(Pointer<?> obj, String name, long value, int searchFlags) throws LibavException {
            Pointer<?> tmp = Pointer.pointerToString(name, Pointer.StringType.C, UTF8);

            tmp = utilLib.av_set_int(obj, tmp.as(Byte.class), value);
            if (tmp == null)
                throw new LibavException("there is no option with name " + name);
        }
    }
    
    private static class OptSetLongFunction implements ISetLongFunction {
        @Override
        public void setLong(Pointer<?> obj, String name, long value, int searchFlags) throws LibavException {
            Pointer<?> tmp = Pointer.pointerToString(name, Pointer.StringType.C, UTF8);

            int res = utilLib.av_opt_set_int(obj, tmp.as(Byte.class), value, searchFlags);
            if (res != 0)
                throw new LibavException(res);
        }
    }
    
    private static class SetDoubleFunction implements ISetDoubleFunction {
        @Override
        public void setDouble(Pointer<?> obj, String name, double value, int searchFlags) throws LibavException {
            Pointer<?> tmp = Pointer.pointerToString(name, Pointer.StringType.C, UTF8);

            tmp = utilLib.av_set_double(obj, tmp.as(Byte.class), value);
            if (tmp == null)
                throw new LibavException("there is no option with name " + name);
        }
    }
    
    private static class OptSetDoubleFunction implements ISetDoubleFunction {
        @Override
        public void setDouble(Pointer<?> obj, String name, double value, int searchFlags) throws LibavException {
            Pointer<?> tmp = Pointer.pointerToString(name, Pointer.StringType.C, UTF8);

            int res = utilLib.av_opt_set_double(obj, tmp.as(Byte.class), value, searchFlags);
            if (res != 0)
                throw new LibavException(res);
        }
    }
    
}
