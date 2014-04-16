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
package org.libav.util;

import org.libav.avutil.bridge.AVRational;

/**
 * Immutable rational number.
 * 
 * @author Ondrej Perutka
 */
public class Rational extends Number implements Comparable<Rational> {
    
    private long num;
    private long den;
    
    /**
     * Create a new rational number.
     * 
     * @param num numerator
     * @param den denominator
     */
    public Rational(long num, long den) {
        this.num = num;
        this.den = den;
    }
    
    /**
     * Create a new rational number from the given AVRational.
     * @param r 
     */
    public Rational(AVRational r) {
        this.num = r.num();
        this.den = r.den();
    }
    
    /**
     * Create a new rational number and set the denominator to 1.
     * 
     * @param nr numerator
     */
    public Rational(long nr) {
        this(nr, 1);
    }
    
    /**
     * Multiple this rational number with the given one and return the 
     * normalized result.
     * 
     * @param r a rational number
     * @return normalized result
     */
    public Rational mul(Rational r) {
        long n = this.num * r.num;
        long d = this.den * r.den;
        long gcd = gcd(n, d);
        return new Rational(n / gcd, d / gcd);
    }
    
    /**
     * Divide this rational number by the given one and return the normalized
     * result.
     * 
     * @param r a rational number
     * @return normalized result
     */
    public Rational div(Rational r) {
        long n = this.num * r.den;
        long d = this.den * r.num;
        long gcd = gcd(n, d);
        return new Rational(n / gcd, d / gcd);
    }
    
    /**
     * Multiple this rational number with the given number and return the 
     * normalized result.
     * 
     * @param num a number
     * @return normalized result
     */
    public Rational mul(long num) {
        long n = this.num * num;
        long gcd = gcd(n, this.den);
        return new Rational(n / gcd, this.den / gcd);
    }
    
    /**
     * Divide this rational number by the given number and return the normalized
     * result.
     * 
     * @param num a number
     * @return normalized result
     */
    public Rational div(long num) {
        long d = this.den * num;
        long gcd = gcd(this.num, d);
        return new Rational(this.num / gcd, d / gcd);
    }
    
    /**
     * Exchange the numerator and the denominator and return the result.
     * 
     * @return inverted number
     */
    public Rational invert() {
        return new Rational(this.den, this.num);
    }
    
    /**
     * Normalize this rational number and return the result. It will divide
     * the numerator and the denominator by their greatest common divider.
     * 
     * @return normalized rational
     */
    public Rational normalize() {
        long gcd = gcd(this.num, this.den);
        return new Rational(this.num / gcd, this.den / gcd);
    }

    /**
     * Get numerator.
     * 
     * @return numerator
     */
    public long getDenominator() {
        return den;
    }

    /**
     * Get denominator.
     * 
     * @return denominator
     */
    public long getNumerator() {
        return num;
    }

    @Override
    public int intValue() {
        return (int)(num / den);
    }

    @Override
    public long longValue() {
        return num / den;
    }

    @Override
    public float floatValue() {
        return (float)num / (float)den;
    }

    @Override
    public double doubleValue() {
        return (double)num / (double)den;
    }

    @Override
    public int compareTo(Rational t) {
        long l = this.num * t.den;
        long r = this.den * t.num;
        
        if (l < r)
            return -1;
        else if (l > r)
            return 1;
        
        return 0;
    }

    @Override
    public String toString() {
        return num + "/" + den;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Rational other = (Rational) obj;
        if (this.num != other.num)
            return false;
        
        return this.den == other.den;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (int) (this.num ^ (this.num >>> 32));
        hash = 89 * hash + (int) (this.den ^ (this.den >>> 32));
        return hash;
    }
    
    private static long gcd(long a, long b) {
        if (a < 0)
            a = -a;
        if (b < 0)
            b = -b;
        
        long tmp;
        while (b > 0) {
            tmp = a % b;
            a = b;
            b = tmp;
        }
        
        return a;
    }
    
}
