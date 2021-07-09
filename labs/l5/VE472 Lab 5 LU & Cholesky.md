# VE472 Lab 5 LU & Cholesky

In this lab, I will give a brief introduction to LU decomposition.

## Motivation

A very basic problem in Big Data is solving linear equations, e.g.
$$
\left\{\begin{array}{rl}
2x_1+x_2+3x_3&=5\\
4x_1-x_2+3x_3&=7\\
-2x_1+5x_2+5x_3&=3\\
\end{array}\right.
$$
We can write it as
$$
\begin{bmatrix}
2&1&3\\
4&-1&3\\
-2&5&5
\end{bmatrix}\begin{bmatrix}
x_1\\x_2\\x_3
\end{bmatrix}=\begin{bmatrix}
5\\7\\3
\end{bmatrix}
$$
or $\mathbf{Ax}=\mathbf{b}$. This is very slow when size of $\mathbf{A}$ becomes large. We can think of a way that can write $\mathbf{A}$ as $\mathbf{A}=\mathbf{LU}$, where $\mathbf{L}$ lis lower triangular and $\mathbf{U}$ is upper triangular, such that the equation becomes
$$
\mathbf{LUx}=\mathbf{b}\Rightarrow \left\{\begin{array}{ll}
\text{solve }\mathbf{y} \text{ for }\mathbf{Ly}=\mathbf{b}\\
\text{solve }\mathbf{x} \text{ for }\mathbf{Ux}=\mathbf{y}
\end{array}\right.
$$
which can be very easily done by ==backward substitution==, as we can see. 
$$
\begin{bmatrix}
2&1&3\\
0&-3&-3\\
0&0&2
\end{bmatrix}\begin{bmatrix}
x_1\\x_2\\x_3
\end{bmatrix}=\begin{bmatrix}
5\\-3\\2
\end{bmatrix}\Rightarrow \mathbf{x}=\begin{bmatrix}
1\\0\\1
\end{bmatrix}
$$
But how can this happen?

## Basic LU Decomposition

### Gaussian Elimination

A common solution to this problem is ==Gaussian Elimination==, take the above $\mathbf{A}$ as an example,

1.  To make the first element on second line to be zero, we need `row_2 += -2 * row_1`.
    $$
    \begin{bmatrix}
    2&1&3\\
    4&-1&3\\
    -2&5&5
    \end{bmatrix}\sim\begin{bmatrix}
    2&1&3\\
    0&-3&-3\\
    -2&5&5
    \end{bmatrix}
    $$

2.  To make the first element on third line to be zero, we need `row_3 += row_1`.
    $$
    \begin{bmatrix}
    2&1&3\\
    0&-3&-3\\
    -2&5&5
    \end{bmatrix}\sim\begin{bmatrix}
    2&1&3\\
    0&-3&-3\\
    0&6&8
    \end{bmatrix}
    $$

3.  To make the second element on third line to be zero, we need `row_3 += 2 * row_2`.
    $$
    \begin{bmatrix}
    2&1&3\\
    0&-3&-3\\
    0&6&8
    \end{bmatrix}\sim\begin{bmatrix}
    2&1&3\\
    0&-3&-3\\
    0&0&2
    \end{bmatrix}=\mathbf{U}
    $$

And we have created an upper triangular matrix.

### Elementary Matrices

We can use matrices to represent the Gaussian elimination steps. For example, doing `row_2 += 2 * row_1` is equivalent to doing a matrix multiplication,
$$
\mathbf{E}_{(-2)1,2}\mathbf{A}=\begin{bmatrix}
1&0&0\\
-2&1&0\\
0&0&1\\
\end{bmatrix}\begin{bmatrix}
-&a_1&-\\
-&a_2&-\\
-&a_3&-\\
\end{bmatrix}=\begin{bmatrix}
-&a_1&-\\
-&-2\times a_1 +a_2&-\\
-&a_3&-\\
\end{bmatrix}
$$
We can write that
$$
\mathbf{E}_{(2)2,3}\mathbf{E}_{(1)1,3}\mathbf{E}_{(-2)1,2}\mathbf{A}=\mathbf{U}
$$
So
$$
\begin{array}{rl}
\mathbf{E}_{(2)2,3}\mathbf{E}_{(1)1,3}\mathbf{E}_{(-2)1,2}\mathbf{A}&=\mathbf{U}\\
\mathbf{A}&=\mathbf{E}_{(-2)1,2}^{-1}\mathbf{E}_{(1)1,3}^{-1}\mathbf{E}_{(2)2,3}^{-1}\mathbf{U}\\
&=\mathbf{E}_{(2)1,2}\mathbf{E}_{(-1)1,3}\mathbf{E}_{(-2)2,3}\mathbf{U}\\
&=\begin{bmatrix}
1&0&0\\
2&1&0\\
0&0&1\\
\end{bmatrix}\begin{bmatrix}
1&0&0\\
0&1&0\\
-1&0&1\\
\end{bmatrix}\begin{bmatrix}
1&0&0\\
0&1&0\\
0&-2&1\\
\end{bmatrix}\mathbf{U}\\
&=\begin{bmatrix}
1&0&0\\
2&1&0\\
-1&-2&1\\
\end{bmatrix}\mathbf{U}
\end{array}
$$
And we get $\mathbf{L}$. Generally speaking, all invertible matrix has its LU decomposition.

Not all matrices can be done this way, and sometimes we need some other permutation matrix, but we won't go deep into that.

**Cons:** based on Gaussian elimination, maybe unstable and slow.

## Cholesky Decomposition

If $\mathbf{A}$ is symmetric positive definite,
$$
\mathbf{A}=\mathbf{A}^T \qquad\text{and}\qquad \forall x\in\mathbb{R}^n\backslash 0: x^T\mathbf{A}x>0
$$
Then we can write $\mathbf{A}=\mathbf{CC}^T$.
$$
\mathbf{A}=\begin{bmatrix}
c_{11}&&&&\\
c_{21}&c_{22}&&&\\
c_{31}&c_{32}&c_{33}&&\\
\vdots&\vdots&\ddots&\ddots&\\
c_{n1}&c_{n2}&c_{n3}&\dots&c_{nn}
\end{bmatrix}\begin{bmatrix}
c_{11}&c_{21}&c_{31}&\dots&c_{n1}\\
&c_{22}&c_{32}&\dots&c_{n2}\\
&&c_{33}&\dots&c_{n3}\\
&&&\ddots&\vdots\\
&&&&c_{nn}
\end{bmatrix}
$$
So as we can see,
$$
a_{11}=c_{11}^2\\
a_{12}=c_{11}c_{21}\\
a_{22}=c_{21}^2 + c_{22}^2\\
$$
and we can calculate the elements in $\mathbf{C}$ accordingly,
$$
c_{11}=\sqrt{a_{11}}\\
c_{21}=a_{12}/c_{11}\\
c_{22}=\sqrt{a_{22}-c_{21}^2}
$$
and so on... We can use the algorithm on the lab manual to calculate the whole matrix.